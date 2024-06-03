import { Component, OnDestroy, OnInit } from '@angular/core';
import { Observable, Subscription, debounceTime, of, take } from 'rxjs';
import { IArticle } from 'src/app/interfaces/IArticle';
import { ArticleService } from 'src/app/services/article.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent implements OnInit, OnDestroy {

  orderAsc = false

  articles: IArticle[] = []
  subscription! : Subscription

  constructor(private articleService : ArticleService) { }

  ngOnInit(): void {
    this.fetchArticles()
  }

  fetchArticles(): void {
    const order = this.orderAsc ? 'asc' : 'desc';
    if(this.subscription) this.subscription.unsubscribe()
    this.subscription = this.articleService.allByOrder$(order).pipe(take(1)).pipe(debounceTime(300)).subscribe(datas => this.articles = datas)
  }

  switchOrder() : void {
    this.orderAsc = !this.orderAsc
    this.fetchArticles()
  }

  ngOnDestroy(): void {
    if(this.subscription) this.subscription.unsubscribe()
  }

}
