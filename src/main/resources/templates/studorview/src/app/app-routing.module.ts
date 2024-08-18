import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { JobEstablishmentComponent } from './job-establishment/job-establishment.component';

const routes: Routes = [
  // {path: '/home', component: HomeComponent},
  // {path: '/job-establishment', component: JobEstablishmentComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
