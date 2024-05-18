import { Component, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { NavigationEnd, Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'front';
  @ViewChild('sidenav') sidenav!: MatSidenav;

  showHeader = true;
  showNav = true;

  constructor(private router: Router) {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.handleNavigation(event.urlAfterRedirects);
      }
    });
  }

  handleNavigation(url: string) {
    // header not needed on the homepage
    const showHeaderUrls = ['/'];
    this.showHeader = !showHeaderUrls.some(urlPath => url.includes(urlPath) && url.length === 1);

    // url where the header is needed but not the nav
    const showNavUrls = ['/login', '/register'];
    this.showNav = !showNavUrls.some(urlPath => url.includes(urlPath));
  }

  openMenu() : void {
    this.sidenav.open()
  }

  closeMenu() : void {
    this.sidenav.close()
  }
}
