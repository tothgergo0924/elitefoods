import { Component } from '@angular/core';
import { FooterComponent } from '../footer/footer.component';
import { NavbarComponent } from '../navbar/navbar.component';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthServiceService } from '../auth-service.service';

import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FooterComponent,NavbarComponent,FormsModule,CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  credentials={
    username:'',
    password:''
  };
  message:string| null=null;
  token:string| null=null;
  constructor(private authService:AuthServiceService,private router:Router){}

  onSubmit(){
    this.authService.login(this.credentials).subscribe({
      next:(response)=>{
        this.token=response.token;
        this.message='Login successful!';
        sessionStorage.setItem('password',this.credentials.password);
        sessionStorage.setItem('authToken',this.token);
        this.router.navigate(["/home"]);
      },
      error:(error)=>{
        console.log('Login error:', error);
        this.message='Login failed, please try again.';
      }
    });
  }
}
