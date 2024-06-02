import { Component, OnDestroy, OnInit } from '@angular/core';
import { Observable, Subject, Subscription, first, of, take, takeUntil } from 'rxjs';
import { ITopic } from 'src/app/interfaces/ITopic';
import { AuthService } from 'src/app/services/auth.service';
import { TopicService } from 'src/app/services/topic.service';

@Component({
  selector: 'app-topics',
  templateUrl: './topics.component.html',
  styleUrls: ['./topics.component.scss']
})
export class TopicsComponent implements OnInit, OnDestroy {

  retrievedTopics : ITopic[] = []
  subscriptions : Subscription[] = []
  userId! : number
  unsubAllObs$ = new Subject<void>()

  constructor(private topicService : TopicService, private authService : AuthService) { }

  ngOnInit(): void {
    // retrieve the user id from the claims into the jwt
    this.userId = this.authService.getIdClaimFromAccessToken();
    // display the topics
    this.refreshTopics()
  }

  refreshTopics(): void{
    const sub = this.topicService.all$().pipe(takeUntil(this.unsubAllObs$)).subscribe(datas => this.retrievedTopics = datas)
    this.subscriptions.forEach(sub => sub.unsubscribe())
    this.subscriptions.push(sub)
  }

  isUserSubscribed(topic : ITopic) : boolean {
    return topic.users.some(user => user.id == this.userId)
  }

  subscribe(event: any) : void{
    const sub = this.topicService.subscribe$(event.topicId).pipe(takeUntil(this.unsubAllObs$)).subscribe(_ => this.refreshTopics())
    this.subscriptions.push(sub)
  }

  unsubscribe(event: any) : void{
    this.topicService.unsubscribe$(event.topicId).pipe(takeUntil(this.unsubAllObs$)).subscribe(_ => this.refreshTopics())
  }

  // unsub all from all observables with .pipe(takeUntil(this.unsubAllObs$))
  ngOnDestroy(): void {
    this.unsubAllObs$.next()
    this.unsubAllObs$.complete()
  }
}
