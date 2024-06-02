import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { IJwtResponse } from '../interfaces/Responses/IJwtResponse';
import { Observable } from 'rxjs';
import { ILoginRequest } from '../interfaces/Requests/ILoginRequest';
import { IRegisterRequest } from '../interfaces/Requests/IRegisterRequest';
import { IUpdateCredentialsRequest } from '../interfaces/Requests/IUpdateCredentialsRequest';
import { ICredentialsResponse } from '../interfaces/Responses/ICredentialsResponse';
import { jwtDecode } from "jwt-decode";
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private pathService = 'api/auth';

  constructor(private httpClient: HttpClient, private storageService : StorageService) {
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
    if(this.storageService.getJwt()) return true
    return false
  }

  getIdClaimFromAccessToken(): any {
    const token = this.storageService.getJwt();
    if (token) {
      const decodedToken = this.getDecodedAccessToken(token);
      if (decodedToken) {
        return decodedToken.id;
      }
    }
    return null;
  }

  getLoggedUserInfos() : {jwt : string, username : string}{
    return this.storageService.getUserCredentials()
  }

  getDecodedAccessToken(token: string): any {
    try {
      return jwtDecode(token);
    } catch(Error) {
      return null;
    }
  }

}
