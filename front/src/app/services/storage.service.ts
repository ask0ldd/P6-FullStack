import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

  // flesh the local storage
  flush(){
    localStorage.removeItem("token")
    localStorage.removeItem("username")
  }

  getJwt(){
    return localStorage.getItem('token')
  }

  // get the jwt & the username out of storage
  getUserCredentials() : {username : string, jwt : string}{
    return {username : localStorage.getItem("username") || '', jwt : localStorage.getItem('token') || ''}
  }

  // set the jwt & the username into the localstorage
  setUserCredentials({token, username} : {token : string, username : string}){
    localStorage.setItem('token', token);
    localStorage.setItem('username', username)
  }
}
