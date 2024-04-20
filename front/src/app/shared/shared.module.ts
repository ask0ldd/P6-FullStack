import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from '../components/header/header.component';
import { TopicCardComponent } from '../components/topic-card/topic-card.component';
import { HttpClientModule } from '@angular/common/http';
import { TruncatePipe } from '../pipes/truncate.pipe';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [HeaderComponent, TopicCardComponent, TruncatePipe],
  imports: [
    CommonModule,
    HttpClientModule,
    FormsModule
  ],
  exports:[
    HeaderComponent, 
    TopicCardComponent, 
    TruncatePipe,
    FormsModule,
    HttpClientModule,
  ],
})
export class SharedModule { }