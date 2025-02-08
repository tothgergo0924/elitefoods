import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterModule, RouterOutlet } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { NavbarComponent } from './navbar/navbar.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { appConfig } from './app.config';


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule,NavbarComponent, RouterOutlet, HomeComponent,RouterLink,RouterModule,HttpClientModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {


  constructor(){

  }
  ngOnInit():void{
   
  }
}
