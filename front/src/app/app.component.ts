import { Component, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'front';
  @ViewChild('sidenav') sidenav!: MatSidenav;

  openMenu() : void {
    this.sidenav.open()
  }

  closeMenu() : void {
    this.sidenav.close()
  }
}
