import { Component, OnDestroy, OnInit } from '@angular/core';
import { Observable, Subscription, first, of, take } from 'rxjs';
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

  constructor(private topicService : TopicService, private authService : AuthService) { }

  ngOnInit(): void {
    // retrieve the user id from the claims into the jwt
    this.userId = this.authService.getIdClaimFromAccessToken();
    // display the topics
    this.refreshTopics()
  }

  refreshTopics(): void{
    const sub = this.topicService.all$().pipe(take(1)).subscribe(datas => this.retrievedTopics = datas)
    this.subscriptions.forEach(sub => sub.unsubscribe())
    this.subscriptions.push(sub)
  }

  isUserSubscribed(topic : ITopic) : boolean {
    return topic.users.some(user => user.id == this.userId)
  }

  subscribe(event: any) : void{
    this.topicService.subscribe$(event.topicId).pipe(take(1)).subscribe(_ => this.refreshTopics())
  }

  unsubscribe(event: any) : void{
    this.topicService.unsubscribe$(event.topicId).pipe(take(1)).subscribe(_ => this.refreshTopics())
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe())
  }
}
