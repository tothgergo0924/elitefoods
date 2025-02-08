import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthServiceService } from '../auth-service.service';
import { Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule,RouterModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  user = {
    felhasznalonev: '',
    jelszo: '',
    keresztnev: '',
    vezeteknev: '',
    email_cim: '',
    telefonszam: '',
    cim: {
      orszag: '',
      varos: '',
      iranyitoszam: '',
      kozterulet: '',
      hazszam: ''
    }
  };
  conditions: boolean=false;
  jelszoConfirm: string  = '';
  message: string | null = null;
  success:boolean=false;
  constructor(private authService: AuthServiceService,private route:Router) {}

  onSubmit() {

    if(!this.conditions){
      this.message="You must accept the terms and conditions to register!";
    }else if (this.jelszoConfirm !== this.user.jelszo ) {
      this.message = "Passwords don't match!";
    }else if(this.user.jelszo.length<8){
      this.message = "Password must contain 8 letters.";
    }else if(this.user.felhasznalonev.length===0 || this.user.vezeteknev.length===0 || this.user.keresztnev.length===0 || this.user.email_cim.length===0 ||this.user.telefonszam.length===0 || this.user.cim.hazszam.length===0 ||this.user.cim.iranyitoszam.length===0 ||this.user.cim.kozterulet.length===0 ||this.user.cim.orszag.length===0 ||this.user.cim.varos.length===0){
      this.message="You have to fill out every input!";
    }else {
      this.authService.register(this.user).subscribe({
        next: (response) => {
            this.jelszoConfirm='';
            this.user = {
              felhasznalonev: '',
              jelszo: '',
              keresztnev: '',
              vezeteknev: '',
              email_cim: '',
              telefonszam: '',
              cim: {
                orszag: '',
                varos: '',
                iranyitoszam: '',
                kozterulet: '',
                hazszam: ''
              }
            };
            this.success=true;
            this.message='';
            
        },
        error: (error) => {
          console.error('Registration error: ', error);
          this.message = error.error;
        }
      });
    }
  }
}
