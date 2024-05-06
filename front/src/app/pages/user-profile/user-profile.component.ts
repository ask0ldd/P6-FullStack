import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable, Subscription, of, take } from 'rxjs';
import { ITopic } from 'src/app/interfaces/ITopic';
import { IUpdateCredentialsRequest } from 'src/app/interfaces/Requests/IUpdateCredentialsRequest';
import { ICredentialsResponse } from 'src/app/interfaces/Responses/ICredentialsResponse';
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
      "",
      [
        Validators.required,
        Validators.minLength(6)
      ]
    ],
    email: [
      "",
      [
        Validators.required,
        Validators.email
      ]
    ]
  });

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private topicService : TopicService,
    private router : Router,
  ) {
  }

  ngOnInit(): void {
    this.refreshTopics()
    this.getUsercredentials()
  }

  onCredentialsSubmit() : void {
    const newCredentials = this.updateCredentialsForm.value as IUpdateCredentialsRequest
    console.log(newCredentials)
    this.authService.updateCredentials$(newCredentials).pipe(take(1)).subscribe({
      next : _ => {
        this.authService.flushStorage()
        this.router.navigate(['login'])
      },
      error : error => console.log(error?.error)
    })
  }

  unsubscribeFromTopic(event: any) : void {
    this.topicService.unsubscribe$(event.topicId, this.userId).pipe(take(1)).subscribe(_ => this.refreshTopics())
  }

  refreshTopics(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe())
    const sub = this.topicService.allTopicsAUserIsSubscribedTo$(this.userId).subscribe(datas => this.retrievedTopics = datas)
    this.subscriptions.push(sub)
  }

  getUsercredentials() : void {
    this.authService.getCredentials$().pipe(take(1)).subscribe((datas : ICredentialsResponse) => {
      this.updateCredentialsForm.get('email')!.setValue(datas.email)
      this.updateCredentialsForm.get('username')!.setValue(datas.username)
    })
  }

  logout() : void {
    this.authService.flushStorage()
    this.router.navigateByUrl('login')
  }

}
