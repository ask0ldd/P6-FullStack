import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ArticlesRoutingModule } from './articles-routing.module';
import { DetailsComponent } from './details/details.component';
import { FormComponent } from './form/form.component';
import { SharedModule } from '../shared/shared.module';
import { ListComponent } from './list/list.component';

@NgModule({
  declarations: [
    DetailsComponent,
    FormComponent,
    ListComponent,
  ],
  imports: [
    CommonModule,
    ArticlesRoutingModule,
    SharedModule,
  ]
})
export class ArticlesModule {
}
