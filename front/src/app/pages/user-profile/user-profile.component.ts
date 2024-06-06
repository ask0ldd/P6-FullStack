import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subject, Subscription, debounceTime, take, takeUntil } from 'rxjs';
import { ITopic } from 'src/app/interfaces/ITopic';
import { IUpdateCredentialsRequest } from 'src/app/interfaces/Requests/IUpdateCredentialsRequest';
import { ICredentialsResponse } from 'src/app/interfaces/Responses/ICredentialsResponse';
import { AuthService } from 'src/app/services/auth.service';
import { StorageService } from 'src/app/services/storage.service';
import { TopicService } from 'src/app/services/topic.service';

@Component({
  selector: 'app-userprofile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit, OnDestroy {

  retrievedTopics! : ITopic[]
  subscriptions : Subscription[] = []
  errorMessage : any = null
  unsubAllObs$ = new Subject<void>()

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
    private storageService : StorageService,
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
      this.authService.updateCredentials$(newCredentials).pipe(takeUntil(this.unsubAllObs$)).subscribe({
        next : _ => 
        {
          this.storageService.flush()
          this.router.navigate(['login'])
        },
        error : () => {
          this.errorMessage = "Identifiants invalides."
        }
    })
    } else {
      this.errorMessage = "Identifiants invalides."
    }
  }

  unsubscribeFromTopic(event: any) : void {
    this.topicService.unsubscribe$(event.topicId).pipe(takeUntil(this.unsubAllObs$)).subscribe(_ => this.refreshTopics())
  }

  // refresh the list of topics the user is subscribed to
  refreshTopics(): void {
    const sub = this.topicService.allTopicsAUserIsSubscribedTo$().pipe(takeUntil(this.unsubAllObs$)).pipe(debounceTime(300)).subscribe(datas => this.retrievedTopics = datas)
    this.subscriptions.forEach(sub => sub.unsubscribe())
    this.subscriptions.push(sub)
  }

  // retrieves the current users credentials
  getUsercredentials() : void {
    this.authService.getCredentials$().pipe(takeUntil(this.unsubAllObs$)).subscribe((datas : ICredentialsResponse) => 
      {
        this.updateCredentialsForm.get('email')!.setValue(datas.email)
        this.updateCredentialsForm.get('username')!.setValue(datas.username)
      }
    )
  }

  logout() : void {
    this.storageService.flush()
    this.router.navigateByUrl('login')
  }

  // unsubing from all observables with .pipe(takeUntil(this.unsubAllObs$))
  ngOnDestroy(): void {
    this.unsubAllObs$.next()
    this.unsubAllObs$.complete()
  }
}
