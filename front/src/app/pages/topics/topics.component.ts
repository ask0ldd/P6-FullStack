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

  userId : number = 1 // !!! should be retrieved from token
  retrievedTopics : ITopic[] = []
  subscriptions : Subscription[] = []

  constructor(private topicService : TopicService) { }

  ngOnInit(): void {
    this.refreshTopics()
  }

  refreshTopics(): void{
    this.subscriptions.forEach(sub => sub.unsubscribe())
    const sub = this.topicService.all$().subscribe(datas => this.retrievedTopics = datas)
    this.subscriptions.push(sub)
  }

  isUserSubscribed(topic : ITopic) : boolean {
    return topic.users.some(user => user.id == this.userId)
  }

  subscribe(event: any) : void{
    this.topicService.subscribe$(event.topicId, this.userId).pipe(take(1)).subscribe(_ => this.refreshTopics())
  }

  unsubscribe(event: any) : void{
    this.topicService.unsubscribe$(event.topicId, this.userId).pipe(take(1)).subscribe(_ => this.refreshTopics())
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe())
  }
}
