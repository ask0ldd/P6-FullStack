import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from '../components/header/header.component';
import { TopicCardComponent } from '../components/topic-card/topic-card.component';
import { HttpClientModule } from '@angular/common/http';
import { TruncatePipe } from '../pipes/truncate.pipe';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [HeaderComponent, TopicCardComponent, TruncatePipe],
  imports: [
    CommonModule,
    RouterModule,
  ],
  exports:[
    HeaderComponent, 
    TopicCardComponent, 
    TruncatePipe,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule,
  ],
})
export class SharedModule { }