import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
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
        Validators.minLength(3)
      ]
    ],
    password: [
      '',
      [
        Validators.required,
        Validators.minLength(8)
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
    this.authService.login$(loginregisterRequest).subscribe((jwtResponse : IJwtResponse )=> {
      localStorage.setItem('token', jwtResponse.token);
      localStorage.setItem('username', jwtResponse.username)
    })
  }

}
