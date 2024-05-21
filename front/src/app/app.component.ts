import { Component, HostListener, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { NavigationEnd, Router } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnDestroy {
  
  title = 'front'
  routerSub!: Subscription

  @ViewChild('sidenav') sidenav!: MatSidenav

  showHeader = true
  showNav = true
  isProfileActive = false

  constructor(private router: Router) {
    this.routerSub = this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.handleNavigation(event.urlAfterRedirects)
      }
    })
  }

  handleNavigation(url: string) {
    // header not needed on the homepage
    if(url == "/") this.showHeader = false

    // url where the nav part of the header isn't needed
    const showNavUrls = ['/login', '/register']
    this.showNav = !showNavUrls.some(urlPath => url.includes(urlPath))
    if(showNavUrls.some(urlPath => url.includes(urlPath))){
      const windowWidth = window.innerWidth
      this.showHeader = windowWidth < 600 ? false : true
    }

    this.isProfileActive = url == "/profile" ? true : false
  }

  openMenu() : void {
    this.sidenav.open()
  }

  closeMenu() : void {
    this.sidenav.close()
  }

  @HostListener('window:resize', ['$event'])
  onResize() {
    const windowWidth = window.innerWidth
    if(this.showNav === false && windowWidth < 600) this.showHeader = false
    if(this.showNav === false && windowWidth >= 600) this.showHeader = true
    if(windowWidth < 500) this.closeMenu()
  }

  ngOnDestroy(): void {
    this.routerSub.unsubscribe()
  }
}
