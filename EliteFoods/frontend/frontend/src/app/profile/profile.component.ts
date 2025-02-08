import { Component } from '@angular/core';
import { UsercrudComponent } from '../usercrud/usercrud.component';
import { AuthServiceService } from '../auth-service.service';
import { AuthResponse, Order, ProfileSave, ProfileService } from '../profile.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Felhasznalo } from '../usercrud.service';
import { response } from 'express';
import { Router } from '@angular/router';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {
  constructor(private profileService:ProfileService,private authService:AuthServiceService,private router:Router){
  }
  dataListed:any={felhasznalonev: '',jelszo:'',
  keresztnev: '',
  vezeteknev: '',
  email_cim: '',
  telefonszam: '',
  cim: {
    azonosito:0,
    orszag: '',
    varos: '',
    iranyitoszam: '',
    kozterulet: '',
    hazszam: ''}};
  loading=true;
  error:string='';
  message:string='';
  orders:Order[]=[];
  
  ngOnInit(){
    const username=this.authService.getName();
    if(username!==null){
      this.profileService.getProfile(username).subscribe({
        next:(response)=>{
          this.dataListed=response;
          this.loading=false;
          this.error='';
        },error:(error)=>{
          this.error=error.error;
        }
      });
      this.profileService.getOrders(username).subscribe({
        next:(response)=>{
          this.orders=response;
          this.loading=false;
          this.error='';
        },error:(error)=>{
          this.error=error.error;
        }
      });
      
    }
    
    
  }
  save(){
    const profileEdit:ProfileSave={
      felhasznalonev:this.dataListed.felhasznalonev,jelszo:this.dataListed.jelszo,
        keresztnev: this.dataListed.keresztnev,
        vezeteknev: this.dataListed.vezeteknev,
        email_cim: this.dataListed.email_cim,
        telefonszam: this.dataListed.telefonszam,
        cim: {
          azonosito:this.dataListed.cim.azonosito,
          orszag: this.dataListed.cim.orszag,
          varos: this.dataListed.cim.varos,
          iranyitoszam: this.dataListed.cim.iranyitoszam,
          kozterulet: this.dataListed.cim.kozterulet,
          hazszam: this.dataListed.cim.hazszam}
    };
    const password = sessionStorage.getItem('password');
    if (password !== null) {
        profileEdit.jelszo = password;
    }
        


    this.profileService.saveProfile(profileEdit,this.dataListed.azonosito).subscribe({
      next:(response:AuthResponse)=>{
        sessionStorage.setItem('authToken',response.token);
        this.error='';
  
      },error:(error)=>{
        this.error="Error while updating profile: Email/username is already taken!";
        this.message='';
      }
    });
  }
  deleteOrder(id:number){
    if(confirm('Are you sure you want to delete this order?')){
      this.profileService.deleteOrder(id).subscribe({
        next:()=>{
          this.orders=this.orders.filter(order=>order.azonosito!==id);
          this.message="Order deleted successfully!";
          this.error='';
        },error:(error)=>{
          this.error=error.error;
          this.message='';
        }
      })
    }
  }
  deleteProfile(){
    const username=this.authService.getName();
    if(username!==null){
      if(confirm("Are you sure you want to delete your profile?")){
        this.profileService.deleteProfile(username).subscribe({
          next:()=>{
            this.authService.logout();
            this.message="You have deleted your profile succesfully!";
            this.error='';
            setTimeout(() => {
              this.router.navigate(['/home']); 
            }, 5000);
          },error:(error)=>{
            this.message='';
            this.error="You can't delete your profile!";
          }
        });
      }
    }

  
    
  }
  details(order:Order){
    this.router.navigate([`/details/${order.azonosito}`]);
  }

}
