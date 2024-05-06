import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { take } from 'rxjs';
import { ILoginRequest } from 'src/app/interfaces/Requests/ILoginRequest';
import { IJwtResponse } from 'src/app/interfaces/Responses/IJwtResponse';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

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
    private authService: AuthService) {
  }

  ngOnInit(): void {
  }

  onSubmit(): void{
    const loginregisterRequest = this.loginForm.value as ILoginRequest;
    this.authService.login$(loginregisterRequest).pipe(take(1)).subscribe({
      next : (jwtResponse : IJwtResponse )=> {
        console.log(jwtResponse)
        this.authService.flushStorage()
        localStorage.setItem('token', jwtResponse.token);
        localStorage.setItem('username', jwtResponse.username)
        this.router.navigateByUrl('/articles/list')
      },
      error : (error : any) => console.log(error?.error)
    })
  }

}