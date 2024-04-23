import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, NgForm, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { IArticle } from 'src/app/interfaces/IArticle';
import { ArticleService } from 'src/app/services/article.service';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss']
})
export class FormComponent implements OnInit {

  public articleForm = this.fb.group({
    email: [
      '',
      [
        Validators.required,
        Validators.email
      ]
    ],
    password: [
      '',
      [
        Validators.required,
        Validators.min(3)
      ]
    ]
  });

  constructor(/*private authService: AuthService,*/
    private fb: FormBuilder,
    private router: Router,
    private articleService: ArticleService) {
}

  ngOnInit(): void {
  }

  onSubmit(/*form: NgForm*/){
    const article = this.articleForm?.value as IArticle;
  }

}
