import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable, Subscription, of, take } from 'rxjs';
import { ITopic } from 'src/app/interfaces/ITopic';
import { ArticleService } from 'src/app/services/article.service';
import { TopicService } from 'src/app/services/topic.service';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss']
})
export class FormComponent implements OnInit, OnDestroy {

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
      if(this.articleForm.get("content")?.status != "VALID") this.errorMessage = "Le contenu doit avoir une longueur minimum de 3 caractères."
      if(this.articleForm.get("title")?.status != "VALID") this.errorMessage = "Le titre doit avoir une longueur minimum de 3 caractères."
      if(this.articleForm.get("topicId")?.status != "VALID") this.errorMessage = "Un thème doit être sélectionné."
    }
  }

  ngOnDestroy(): void {
    if(this.subscription) this.subscription.unsubscribe()
  }

}