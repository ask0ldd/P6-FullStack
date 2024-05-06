import { Component, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatMenuTrigger } from '@angular/material/menu';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AppComponent } from 'src/app/app.component';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit, OnDestroy {

  isActivePageProfile = false
  isMenuDisplayed = true
  subscriptions : Subscription[] = []

  constructor(private appComponent: AppComponent, private route : ActivatedRoute) { 
  }

  ngOnInit(): void {
    this.route.url.subscribe(url => {
      
      if(url[0]?.path === "login" || url[0]?.path === "register") {
        this.isMenuDisplayed = false
      }
      else{
        this.isMenuDisplayed = true
      }

      if(url[0]?.path === "profile"){
        this.isActivePageProfile = true
      }
      else{
        this.isActivePageProfile = false
      }
    });
  }

  toggleMenu() : void {
    this.appComponent.openMenu()
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(sub => sub.unsubscribe())
  }

}
