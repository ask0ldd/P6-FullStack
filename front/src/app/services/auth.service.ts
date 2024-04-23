import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IJwtResponse } from '../interfaces/Responses/IJwtResponse';
import { Observable } from 'rxjs';
import { ILoginRequest } from '../interfaces/Requests/ILoginRequest';
import { IRegisterRequest } from '../interfaces/Requests/IRegisterRequest';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private pathService = 'api/auth';

  constructor(private httpClient: HttpClient) {
  }

  login$(loginRequest : ILoginRequest) : Observable<IJwtResponse>{
    return this.httpClient.post<IJwtResponse>('/login', loginRequest)
  }

  register$(registerRequest : IRegisterRequest) : Observable<IJwtResponse>{
    return this.httpClient.post<IJwtResponse>('/register', registerRequest)
  }
}
