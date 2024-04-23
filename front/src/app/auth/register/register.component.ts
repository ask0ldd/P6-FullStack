import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { IRegisterRequest } from 'src/app/interfaces/Requests/IRegisterRequest';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  public registerForm = this.fb.group({
    userName : [
      '',
      [
        Validators.required,
        Validators.min(3)
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
        Validators.min(3)
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

  onSubmit(){
    const registerRequest = this.registerForm.value as IRegisterRequest;
  }

}
