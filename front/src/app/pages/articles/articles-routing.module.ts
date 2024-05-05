import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListComponent } from './list/list.component';
import { FormComponent } from './form/form.component';
import { DetailsComponent } from './details/details.component';
import { AuthGuard } from 'src/app/guards/auth.guard';

const routes: Routes = [
  { path: 'articles/list', canActivate: [AuthGuard], component: ListComponent },
  { path: 'articles/new', canActivate: [AuthGuard], component: FormComponent },
  { path: 'articles/details/:id', canActivate: [AuthGuard], component: DetailsComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ArticlesRoutingModule { }
