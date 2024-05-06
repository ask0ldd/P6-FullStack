import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { take } from 'rxjs';
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
    private authService: AuthService) {
  }

  ngOnInit(): void {
  }

  onSubmit(): void{
    const registerRequest = this.registerForm.value as IRegisterRequest;
    this.authService.register$(registerRequest).pipe(take(1)).subscribe({
      next : (jwtResponse : IJwtResponse ) => {
        console.log(jwtResponse)
        this.authService.flushStorage()
        localStorage.setItem('token', jwtResponse.token);
        localStorage.setItem('username', jwtResponse.username)
        this.router.navigateByUrl('/articles/list')
      },
      error : (error : any) => { this.errorMessage = error?.error?.message}
    })
  }

}
