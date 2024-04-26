import { Injectable } from '@angular/core';
import { INewCommentRequest } from '../interfaces/Requests/INewCommentRequest';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private pathService = 'api/article';

  constructor(private http: HttpClient) {
  }

  create$(newComment : INewCommentRequest) : Observable<any>{
    return this.http.post<any>(`api/article/${newComment.articleId}/comment`, newComment)
  }
}
