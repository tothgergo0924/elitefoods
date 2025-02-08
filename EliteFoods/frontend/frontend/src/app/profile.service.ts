import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Felhasznalo } from './usercrud.service';
import { Product } from './products.service';

export interface ProfileSave{
  felhasznalonev:string,jelszo:string,
    keresztnev: string,
    vezeteknev: string,
    email_cim: string,
    telefonszam: string,
    cim: {
      azonosito:number,
      orszag: string,
      varos: string,
      iranyitoszam: string,
      kozterulet: string,
      hazszam: string}
}

export interface Cart{
  azonosito:number,
  nev:string,
  kep:string,
  ar:number,
  mennyiseg:number
}
export interface AuthResponse {
  token: string;
}

export interface Order{
  azonosito:number,
  osszeg:number,
  datum:string,
  termekek:Product[]
}
@Injectable({
  providedIn: 'root'
})
export class ProfileService {
  private apiUrl="http://161.35.204.146:8080/api/profile";
  constructor(private http:HttpClient) { }

  getProfile(name:string):Observable<any>{
    return this.http.get<any>(`${this.apiUrl}/${name}`);
  }

  saveProfile(user:{felhasznalonev:string,jelszo:string,
    keresztnev: string,
    vezeteknev: string,
    email_cim: string,
    telefonszam: string,
    cim: {
      azonosito:number,
      orszag: string,
      varos: string,
      iranyitoszam: string,
      kozterulet: string,
      hazszam: string}},id:number):Observable<AuthResponse>{
    return this.http.put<AuthResponse>(`${this.apiUrl}/edit/${id}`,user);
  }

  getOrders(username:string):Observable<Order[]>{
    return this.http.get<Order[]>(`${this.apiUrl}/product/${username}`);
  }
  deleteOrder(id:number):Observable<void>{
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`);
  }
  deleteProfile(username:string):Observable<void>{
    return this.http.delete<void>(`${this.apiUrl}/delete/profile/${username}`);
  }
  getOrderById(id:number):Observable<Order>{
    return this.http.get<Order>(`${this.apiUrl}/order/${id}`);
  }
}
