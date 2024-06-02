import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription, take } from 'rxjs';
import { IRegisterRequest } from 'src/app/interfaces/Requests/IRegisterRequest';
import { IJwtResponse } from 'src/app/interfaces/Responses/IJwtResponse';
import { AuthService } from 'src/app/services/auth.service';
import { StorageService } from 'src/app/services/storage.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnDestroy {

  errorMessage = ""
  subscription : Subscription | null = null

  public registerForm : FormGroup = this.fb.group({
    username : [
      '',
      [
        Validators.required,
        Validators.minLength(6)
      ]
    ],
    email: [
      '',
      [
        Validators.required,
        Validators.email
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
    const registerRequest = this.registerForm.value as IRegisterRequest;
    this.subscription = this.authService.register$(registerRequest).pipe(take(1)).subscribe({
      next : (jwtResponse : IJwtResponse ) => {
        this.storageService.flush()
        this.storageService.setUserCredentials({username : jwtResponse.username, token : jwtResponse.token})
        this.router.navigateByUrl('/articles/list')
      },
      error : (error : any) => { 
        this.errorMessage = error.status === 401? "Cet email ne peut être utilisé." : error?.error?.message
      }
    })
  }

  ngOnDestroy(): void {
    if(this.subscription){
      this.subscription.unsubscribe()
    }
  }

}
