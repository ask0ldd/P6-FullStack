import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { IArticle } from '../interfaces/IArticle';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {
  
  private pathService = 'api/article';

  constructor(private http: HttpClient) {
  }

  detail$(id: string): Observable<IArticle> {
    return this.http.get<IArticle>(`${this.pathService}/${id}`);
  }

  all$(): Observable<IArticle[]>{
    // console.log("triggered")
    // this.http.get<IArticle[]>('api/articles').subscribe(data => console.log(data))
    return this.http.get<IArticle[]>('api/articles')
  }
}
