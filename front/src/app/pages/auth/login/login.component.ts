import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription, take } from 'rxjs';
import { ILoginRequest } from 'src/app/interfaces/Requests/ILoginRequest';
import { IJwtResponse } from 'src/app/interfaces/Responses/IJwtResponse';
import { AuthService } from 'src/app/services/auth.service';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnDestroy {

  errorMessage = ""
  private subscription: Subscription | null = null;

  public loginForm : FormGroup = this.fb.group({
    emailOrUsername: [
      '',
      [
        Validators.required,
        Validators.minLength(6)
      ]
    ],
    password: [
      '',
      [
        Validators.required,
        Validators.minLength(8),
        Validators.pattern("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–\\[{}\\]:;',?/*~$^+=<>]).{8,}$")
      ]
    ]
  });

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthService,
    private storageService : StorageService
  ) { }

  onSubmit(): void{
    const loginregisterRequest = this.loginForm.value as ILoginRequest;
    this.subscription = this.authService.login$(loginregisterRequest).pipe(take(1)).subscribe({
      next : (jwtResponse : IJwtResponse )=> {
        // console.log(jwtResponse)
        this.storageService.flush()
        this.storageService.setUserCredentials({username : jwtResponse.username, token : jwtResponse.token})
        this.router.navigateByUrl('/articles/list')
      },
      error : (error : any) => this.errorMessage = error?.error?.message
    })
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe()
    }
  }

}