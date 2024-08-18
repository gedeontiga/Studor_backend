import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { JobEstablishmentComponent } from './job-establishment/job-establishment.component';
import { NavbarComponent } from './navbar/navbar.component';
import { NotesreportFormComponent } from './notesreport-form/notesreport-form.component';
import { provideHttpClient, withFetch } from '@angular/common/http';
import {ReactiveFormsModule} from '@angular/forms';
import { RegisterFormComponent } from './register-form/register-form.component';
import { LoginFormComponent } from './login-form/login-form.component'
// import { MatIconModule } from '@angular/material/icon';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { TypewriterDirective } from './typewriter.directive';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    JobEstablishmentComponent,
    NavbarComponent,
    NotesreportFormComponent,
    RegisterFormComponent,
    LoginFormComponent,
    TypewriterDirective
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    BrowserAnimationsModule
    // MatIconModule
  ],
  providers: [
    provideHttpClient(withFetch()),
    provideAnimationsAsync()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
