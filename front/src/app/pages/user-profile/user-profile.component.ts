import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription, take } from 'rxjs';
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

  retrievedTopics! : ITopic[]
  subscriptions : Subscription[] = []
  errorMessage : any = null

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

  //  dealing with the new credentials for
  onCredentialsSubmit() : void {
    if(this.updateCredentialsForm.valid) {
      const newCredentials = this.updateCredentialsForm.value as IUpdateCredentialsRequest
      console.log(newCredentials)
      this.authService.updateCredentials$(newCredentials).pipe(take(1)).subscribe(_ => 
        {
          this.authService.flushStorage()
          this.router.navigate(['login'])
        }
      )
    } else {
      this.errorMessage = "Identifiants invalides."
    }
  }

  unsubscribeFromTopic(event: any) : void {
    this.topicService.unsubscribe$(event.topicId).pipe(take(1)).subscribe(_ => this.refreshTopics())
  }

  refreshTopics(): void {
    const sub = this.topicService.allTopicsAUserIsSubscribedTo$().pipe(take(1)).subscribe(datas => this.retrievedTopics = datas)
    this.subscriptions.forEach(sub => sub.unsubscribe())
    this.subscriptions.push(sub)
  }

  // retrieves the current users credentials
  getUsercredentials() : void {
    this.authService.getCredentials$().pipe(take(1)).subscribe((datas : ICredentialsResponse) => 
      {
        this.updateCredentialsForm.get('email')!.setValue(datas.email)
        this.updateCredentialsForm.get('username')!.setValue(datas.username)
      }
    )
  }

  logout() : void {
    this.authService.flushStorage()
    this.router.navigateByUrl('login')
  }

}
