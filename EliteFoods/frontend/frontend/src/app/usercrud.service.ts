import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpHeaders } from '@angular/common/http';
import { response } from 'express';
import { error } from 'console';

export interface Felhasznalo{
  azonosito:number,
  jelszo:string,
  cim: Cim,
  keresztnev:string,
  vezeteknev:string,
  email_cim:string,
  felhasznalonev:string,
  telefonszam:string
}
export interface Cim{
  azonosito: number,
  orszag: string,
  varos:string,
  iranyitoszam:string,
  kozterulet:string,
  hazszam:string
}
export interface FelhasznaloEdit{
  keresztnev:string,
  email_cim:string,
  telefonszam:string
}



@Injectable({
  providedIn: 'root'
})
export class UsercrudService {
  private apiUrl="http://161.35.204.146:8080/api/usercrud";
  constructor(private http:HttpClient) { }


  add(userRoleDTO:{felhasznalo:{felhasznalonev:string,telefonszam:string,email_cim:string,vezeteknev:string,keresztnev:string,jelszo:string,cim:{orszag:string,varos:string,iranyitoszam:string,kozterulet:string,hazszam:string}},
    role:{nev:string}})
    :Observable<string>{
      return this.http.post(`${this.apiUrl}/add`,userRoleDTO,{headers: new HttpHeaders({
        'Content-Type': 'application/json'
      }),
      responseType: 'text' // szimpla szöveg válasz
    });
  }

    getUsers():Observable<Felhasznalo[]>{
      return this.http.get<Felhasznalo[]>(this.apiUrl);
    }

    deleteUser(id:number):Observable<void>{
      return this.http.delete<void>(`${this.apiUrl}/delete/${id}`);
    }
    editUser(user:any,id:number):Observable<string>{
      return this.http.put<string>(`${this.apiUrl}/edit/${id}`,user, { responseType: 'text' as 'json' } );
   }
}
