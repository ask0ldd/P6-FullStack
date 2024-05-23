import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { IArticle } from '../interfaces/IArticle';
import { INewArticleRequest } from '../interfaces/Requests/INewArticleRequest';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {
  
  private pathService = 'api/article';

  constructor(private http: HttpClient) {
  }

  // retrieves an article and its details
  detail$(id: string): Observable<IArticle> {
    return this.http.get<IArticle>(`${this.pathService}/${id}`);
  }

  all$(): Observable<IArticle[]>{
    return this.http.get<IArticle[]>('api/articles')
  }

  // retrieve all the articles in a specific order
  allByOrder$(order : string): Observable<IArticle[]>{
    return this.http.get<IArticle[]>('api/articles/' + order)
  }

  // post a new article
  create$(newArticle : INewArticleRequest) : Observable<any>{
    return this.http.post<any>('api/article', newArticle)
  }
}
