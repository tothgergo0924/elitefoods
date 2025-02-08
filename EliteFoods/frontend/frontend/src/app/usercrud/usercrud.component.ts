import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Felhasznalo, FelhasznaloEdit, UsercrudService } from '../usercrud.service';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';

@Component({
  selector: 'app-usercrud',
  standalone: true,
  imports: [CommonModule,FormsModule,ReactiveFormsModule],
  templateUrl: './usercrud.component.html',
  styleUrl: './usercrud.component.css'
})
export class UsercrudComponent {
  constructor(private service:UsercrudService){}
  user = {felhasznalo:{
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
    }},role:{
      nev:''
    }
  };
  jelszoConfirm: string | null = null;
  message: string | null = null;
  users:Felhasznalo[]=[];
  loading=true;
  error:string | null=null;

  onSubmit(){
    if (this.jelszoConfirm !== this.user.felhasznalo.jelszo) {
      this.message = "Passwords don't match!";
    } else {
      if(this.user.role.nev===''){
        this.user.role.nev="felhasználó"
      }
      this.service.add(this.user).subscribe({
        next: (response) => {
            this.jelszoConfirm=null
            this.message = 'User added successfully!';
            this.user = {felhasznalo:{
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
              }},role:{
                nev:''
              }
            };
            this.service.getUsers().subscribe({
              next:(data)=>{
                this.users=data;
                this.loading=false;
              },
              error:(err)=>{
                this.error='Error fetching users!';
                this.loading=false;
              },
            });
      
          
        },
        error: (error) => {
          console.error('Error while adding user: ', error);
          this.message = error.error;
        }
      });
    }
  }
  ngOnInit():void{
    this.service.getUsers().subscribe({
      next:(data)=>{
        this.users=data;
        this.loading=false;
      },
      error:(err)=>{
        this.error='Error fetching users!';
        this.loading=false;
      },
    });
  }
  save(user:any):void{
    
    const editUser:FelhasznaloEdit={keresztnev:user.keresztnev,email_cim:user.email_cim,telefonszam:user.telefonszam};

    // Töröld az 'azonosito' attribútumot
    
    this.service.editUser(editUser,user.azonosito).subscribe(
      ()=>{
        alert("User updated successfully!");
      },
      (error)=>{
        this.error='Failed to update the user:'+error.error;
      }
    );
  }
  deleteUser(id:number){
    if(confirm('Are you sure you want to delete this user?')){
      this.service.deleteUser(id).subscribe(
        ()=>{
          this.users=this.users.filter(user=>user.azonosito!==id);
        },
        (error)=>{
          this.error='Failed to delete user:'+error.error;
        }
      );
    }
  }

}
