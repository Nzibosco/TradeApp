import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { HomeComponent } from './components/home/home.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
//Facebook plugin 
import { FacebookModule } from 'ngx-facebook';
//PrimeNG modules
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MenubarModule} from 'primeng/menubar';
import {ButtonModule}  from 'primeng/button';
import {InputTextModule} from 'primeng/inputtext';
import {CardModule} from 'primeng/card';
import {TableModule} from 'primeng/table';
import { StoreModule } from '@ngrx/store';
import { DashboardModule } from './components/dashboard/dashboard.modules';
import { EffectsModule } from '@ngrx/effects';
import {DialogModule} from 'primeng/dialog';
import {DropdownModule} from 'primeng/dropdown';
import { InputNumberModule } from 'primeng/inputnumber';



import { OrderComponent } from './components/order/order.component';
import { LoginComponent } from './components/login/login.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    HomeComponent,
    DashboardComponent,
    OrderComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    ButtonModule,
    MenubarModule,
    InputTextModule, 
    CardModule,
    TableModule,
    DialogModule,
    DropdownModule,
    InputNumberModule,
    FacebookModule,
    StoreModule.forRoot({}),
    DashboardModule,
    EffectsModule.forRoot([])
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
