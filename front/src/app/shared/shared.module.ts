import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from '../components/header/header.component';
import { TopicCardComponent } from '../components/topic-card/topic-card.component';
import { HttpClientModule } from '@angular/common/http';
import { TruncatePipe } from '../pipes/truncate.pipe';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { MatSidenavModule } from '@angular/material/sidenav';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  declarations: [HeaderComponent, TopicCardComponent, TruncatePipe],
  imports: [
    CommonModule,
    RouterModule,
    MatSidenavModule,
    ReactiveFormsModule,
    HttpClientModule,
    BrowserAnimationsModule
  ],
  exports:[
    HeaderComponent, 
    TopicCardComponent,
    TruncatePipe,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule,
    MatSidenavModule,
    BrowserAnimationsModule
  ],
})
export class SharedModule { }