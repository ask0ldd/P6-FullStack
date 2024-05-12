import { Component, OnDestroy, OnInit } from '@angular/core';
import { Observable, Subscription, first, of, take } from 'rxjs';
import { ITopic } from 'src/app/interfaces/ITopic';
import { TopicService } from 'src/app/services/topic.service';

@Component({
  selector: 'app-topics',
  templateUrl: './topics.component.html',
  styleUrls: ['./topics.component.scss']
})
export class TopicsComponent implements OnInit, OnDestroy {

  retrievedTopics : ITopic[] = []
  subscriptions : Subscription[] = []

  constructor(private topicService : TopicService) { }

  ngOnInit(): void {
    this.refreshTopics()
  }

  refreshTopics(): void{
    const sub = this.topicService.all$().pipe(take(1)).subscribe(datas => this.retrievedTopics = datas)
    this.subscriptions.forEach(sub => sub.unsubscribe())
    this.subscriptions.push(sub)
  }

  isUserSubscribed(topic : ITopic) : boolean {
    // retrieve from token
    const userId = 2
    return topic.users.some(user => user.id == userId)
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
