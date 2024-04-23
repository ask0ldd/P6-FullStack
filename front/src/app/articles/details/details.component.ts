import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable, of } from 'rxjs';
import { IArticle } from 'src/app/interfaces/IArticle';
import { ArticleService } from 'src/app/services/article.service';

@Component({
  selector: 'app-details',
  templateUrl: './details.component.html',
  styleUrls: ['./details.component.scss']
})
export class DetailsComponent implements OnInit {

  article$ : Observable<IArticle> = of()
  urlId! : string | null

  constructor(private articleService : ArticleService, private route: ActivatedRoute) { 
  }

  ngOnInit(): void {
    this.urlId = this.route.snapshot.paramMap.get('id')
    if(this.urlId != null ) this.article$ = this.articleService.detail$(this.urlId)
  }

}
