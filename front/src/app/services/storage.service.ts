import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

  flush(){
    localStorage.removeItem("token")
    localStorage.removeItem("username")
  }

  getJwt(){
    return localStorage.getItem('token')
  }

  getUserCredentials() : {username : string, jwt : string}{
    return {username : localStorage.getItem("username") || '', jwt : localStorage.getItem('token') || ''}
  }

  setUserCredentials({token, username} : {token : string, username : string}){
    localStorage.setItem('token', token);
    localStorage.setItem('username', username)
  }
}
