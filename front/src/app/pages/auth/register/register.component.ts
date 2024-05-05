import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { IRegisterRequest } from 'src/app/interfaces/Requests/IRegisterRequest';
import { IJwtResponse } from 'src/app/interfaces/Responses/IJwtResponse';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  errorMessage!: string

  public registerForm : FormGroup = this.fb.group({
    username : [
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
    const registerRequest = this.registerForm.value as IRegisterRequest;
    this.authService.register$(registerRequest).subscribe({
      next : (jwtResponse : IJwtResponse ) => {
        console.log(jwtResponse)
        localStorage.setItem('token', jwtResponse.token);
      },
      error : (error : any) => { this.errorMessage = error?.error?.message}
    })
  }

}
