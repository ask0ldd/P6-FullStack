import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IJwtResponse } from '../interfaces/Responses/IJwtResponse';
import { Observable } from 'rxjs';
import { ILoginRequest } from '../interfaces/Requests/ILoginRequest';
import { IRegisterRequest } from '../interfaces/Requests/IRegisterRequest';
import { IUpdateCredentialsRequest } from '../interfaces/Requests/IUpdateCredentialsRequest';
import { ICredentialsResponse } from '../interfaces/Responses/ICredentialsResponse';

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

  updateCredentials$(updateCredentialsRequest : IUpdateCredentialsRequest) : Observable<any>{
    return this.httpClient.put<any>(`${this.pathService}/newcredentials`, updateCredentialsRequest)
  }

  
  getCredentials$() : Observable<ICredentialsResponse>{
    return this.httpClient.get<ICredentialsResponse>(`${this.pathService}/credentials`)
  }

  isAuthenticated() : boolean{
    if(localStorage.getItem("token")) return true
    return false
  }

  // !!! create local storage service
  getLoggedUserInfos() : {jwt : string, username : string}{
    const jwt = localStorage.getItem("token")
    const username = localStorage.getItem("username")
    return {jwt : jwt || "", username : username || ""}
  }

  // !!! create local storage service
  flushStorage(){
    localStorage.removeItem("token")
    localStorage.removeItem("username")
  }
}
