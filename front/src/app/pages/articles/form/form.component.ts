import { Component, ElementRef, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable, Subscription, of, take } from 'rxjs';
import { IArticle } from 'src/app/interfaces/IArticle';
import { ITopic } from 'src/app/interfaces/ITopic';
import { ArticleService } from 'src/app/services/article.service';
import { TopicService } from 'src/app/services/topic.service';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss']
})
export class FormComponent implements OnInit, OnDestroy {

  // retrievedTopics : ITopic[] = []
  topics$ : Observable<ITopic[]> = of([])
  errorMessage = ""
  subscription! : Subscription

  public articleForm : FormGroup = this.fb.group({
    topicId: [
      '',
      [
        Validators.required,
        Validators.min(1)
      ]
    ],
    title: [
      '',
      [
        Validators.required,
        Validators.minLength(3)
      ]
    ],
    content: [
      '',
      [
        Validators.required,
        Validators.minLength(3),
        Validators.nullValidator
      ]
    ]
  });

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private articleService: ArticleService,
    private topicService : TopicService,
  ) { }

  // retrieve all topics
  ngOnInit(): void {
    this.topics$ = this.topicService.all$()
  }

  onSubmit(): void{
    console.log(this.articleForm)
    if(this.articleForm.valid) {
      this.errorMessage = ""
      const newArticle = this.articleForm?.value
      this.subscription = this.articleService.create$(newArticle).pipe(take(1)).subscribe({
        next : _ => {
          this.router.navigate(['articles/list'])
        },
        error : error => {
          // deals with the error detected by the back end
          this.errorMessage = error?.error?.message
        }
      })
    } else {
      // deals with the error detected by the front end
      this.errorMessage = ""
      if(this.articleForm.get("content")?.status != "VALID") this.errorMessage = "The content value should have a min length of 3."
      if(this.articleForm.get("title")?.status != "VALID") this.errorMessage = "The title value should have a min length of 3."
      if(this.articleForm.get("topicId")?.status != "VALID") this.errorMessage = "A topic should be selected."
    }
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe()
  }

}