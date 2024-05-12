import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ITopic } from '../interfaces/ITopic';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TopicService {

  private pathService = 'api/topic';

  constructor(private http: HttpClient) { }

  all$(): Observable<ITopic[]>{
    return this.http.get<ITopic[]>('api/topics')
  }

  allTopicsAUserIsSubscribedTo$(): Observable<ITopic[]>{
    return this.http.get<ITopic[]>(`api/topics/byuser`)
  }

  subscribe$(topicId: number): Observable<void>{
    return this.http.post<void>(`api/topic/${topicId}/subscribe`, null)
  } 

  unsubscribe$(topicId: number): Observable<void>{
    return this.http.delete<void>(`api/topic/${topicId}/unsubscribe`)
  } 
}