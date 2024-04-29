import { Component, OnInit } from '@angular/core';
import { Observable, of } from 'rxjs';
import { IArticle } from 'src/app/interfaces/IArticle';
import { ArticleService } from 'src/app/services/article.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit {

  articles$ : Observable<IArticle[]> = this.articleService.all$()

  constructor(private articleService : ArticleService) { }

  ngOnInit(): void {
    // this.articles$ = this.articleService.all$()
  }

}
