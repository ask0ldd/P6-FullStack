import { Component, OnInit } from '@angular/core';
import { Observable, of } from 'rxjs';
import { ITopic } from 'src/app/interfaces/ITopic';
import { TopicService } from 'src/app/services/topic.service';

@Component({
  selector: 'app-userprofile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {

  topics$ : Observable<ITopic[]> = of()
  userId : number = 1 // !!! should be retrieved through token

  constructor(private topicService : TopicService) { }

  ngOnInit(): void {
    this.topics$ = this.topicService.allTopicsAUserIsSubscribedTo$(this.userId)
  }

}
