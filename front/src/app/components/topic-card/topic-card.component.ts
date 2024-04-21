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
  userId : number = 1

  @Output() askParentForSubscription = new EventEmitter<any>();
  @Output() askParentForUnsubscription = new EventEmitter<any>();

  constructor(private topicService : TopicService) { }

  ngOnInit(): void {
  }

  /*subscribe(topicId : number) : void{
    this.topicService.subscribe$(topicId, this.userId).subscribe() // !!! should unsubscribe from observable
  }

  unsubscribe(topicId : number) : void{
    this.topicService.unsubscribe$(topicId, this.userId).subscribe() // !!! should unsubscribe from observable
  }*/

  handleSubscribeClick(){
    this.askParentForSubscription.emit({topicId:this.topicId})
  }

  handleUnsubscribeClick(){
    this.askParentForUnsubscription.emit({topicId:this.topicId})
  }

}
