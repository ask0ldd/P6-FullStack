import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Observable, Subscription, of, take } from 'rxjs';
import { ITopic } from 'src/app/interfaces/ITopic';
import { AuthService } from 'src/app/services/auth.service';
import { TopicService } from 'src/app/services/topic.service';

@Component({
  selector: 'app-userprofile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {

  userId : number = 1 // !!! should be retrieved through token
  retrievedTopics : ITopic[] = []
  subscriptions : Subscription[] = []

  public updateCredentialsForm : FormGroup = this.fb.group({
    username: [
      '',
      [
        Validators.required,
        Validators.minLength(3)
      ]
    ],
    email: [
      '',
      [
        Validators.required,
        Validators.email
      ]
    ]
  });

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private topicService : TopicService) {
  }

  ngOnInit(): void {
    this.refreshTopics()
  }

  onCredentialsSubmit() : void {
    // !!!!
  }

  unsubscribeFromTopic(event: any) : void{
    this.topicService.unsubscribe$(event.topicId, this.userId).pipe(take(1)).subscribe(_ => this.refreshTopics())
  }

  refreshTopics(): void{
    this.subscriptions.forEach(sub => sub.unsubscribe())
    const sub = this.topicService.allTopicsAUserIsSubscribedTo$(this.userId).subscribe(datas => this.retrievedTopics = datas)
    this.subscriptions.push(sub)
  }

  getUsercredentials() : void{
    // !!!
  }

}
