import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { TopicService } from 'src/app/services/topic.service';

@Component({
  selector: 'app-topic-card',
  templateUrl: './topic-card.component.html',
  styleUrls: ['./topic-card.component.scss']
})
export class TopicCardComponent implements OnInit {

  @Input() subscribed : boolean = false
  @Input() title : string = ""
  @Input() description : string = ""
  @Input() topicId : number = 0
  @Input() profileCard : boolean = false
  userId : number = 1

  @Output() askParentForSubscription = new EventEmitter<any>();
  @Output() askParentForUnsubscription = new EventEmitter<any>();

  ngOnInit(): void {
  }

  handleSubscribeClick(){
    this.askParentForSubscription.emit({topicId:this.topicId})
  }

  handleUnsubscribeClick(){
    this.askParentForUnsubscription.emit({topicId:this.topicId})
  }

}
