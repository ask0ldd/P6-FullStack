import { Component, Input } from '@angular/core';
import { AppComponent } from 'src/app/app.component';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {

  @Input() isProfileActive = false
  @Input() showNav: boolean = true;

  constructor(private appComponent: AppComponent) { 
  }

  toggleMenu() : void {
    this.appComponent.openMenu()
  }
}
