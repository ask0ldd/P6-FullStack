import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IJwtResponse } from '../interfaces/Responses/IJwtResponse';
import { Observable } from 'rxjs';
import { ILoginRequest } from '../interfaces/Requests/ILoginRequest';
import { IRegisterRequest } from '../interfaces/Requests/IRegisterRequest';
import { IUpdateCredentialsRequest } from '../interfaces/Requests/IUpdateCredentialsRequest';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private pathService = 'api/auth';

  constructor(private httpClient: HttpClient) {
  }

  login$(loginRequest : ILoginRequest) : Observable<IJwtResponse>{
    return this.httpClient.post<IJwtResponse>(`${this.pathService}/login`, loginRequest)
  }

  register$(registerRequest : IRegisterRequest) : Observable<IJwtResponse>{
    return this.httpClient.post<IJwtResponse>(`${this.pathService}/register`, registerRequest)
  }

  updateCredentials$(updateCredentialsRequest : IUpdateCredentialsRequest) : Observable<IJwtResponse>{
    return this.httpClient.put<IJwtResponse>(`${this.pathService}/updatecredentials`, updateCredentialsRequest)
  }

  getLoggedUserInfos() : {jwt : string, username : string}{
    const jwt = localStorage.getItem("jwt")
    const username = localStorage.getItem("username")
    return {jwt : jwt || "", username : username || ""}
  }
}
