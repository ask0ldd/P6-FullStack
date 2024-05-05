import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable, of, take } from 'rxjs';
import { IArticle } from 'src/app/interfaces/IArticle';
import { ITopic } from 'src/app/interfaces/ITopic';
import { ArticleService } from 'src/app/services/article.service';
import { TopicService } from 'src/app/services/topic.service';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss']
})
export class FormComponent implements OnInit {

  // retrievedTopics : ITopic[] = []
  topics$ : Observable<ITopic[]> = of([])

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
  ) {
}

  ngOnInit(): void {
    // this.topicService.all$().pipe(take(1)).subscribe(datas => this.retrievedTopics = datas)
    this.topics$ = this.topicService.all$()
  }

  onSubmit(): void{
    const newArticle = this.articleForm?.value
    this.articleService.create$(newArticle).subscribe({
      next : _ => {
        this.router.navigate(['articles/list'])
      },
      error : error => console.log(error?.error)
    }) // !!!! unsub
  }

}