import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {
  private apiUrl="http://161.35.204.146:8080/api/auth";
  isBrowser:boolean;
  constructor(private http:HttpClient) { 
    this.isBrowser=typeof window !=='undefined';
  }

  register(userData:{felhasznalonev:string,telefonszam:string,email_cim:string,vezeteknev:string,keresztnev:string,jelszo:string,cim:{orszag:string,varos:string,iranyitoszam:string,kozterulet:string,hazszam:string}})
  :Observable<string>{
    return this.http.post(`${this.apiUrl}/register`,userData,{headers: new HttpHeaders({
      'Content-Type': 'application/json'
    }),
    responseType: 'text' // szimpla szöveg válasz
  });
  }
  login(credentials:{username:string,password:string}):Observable<{token:string}>{
    return this.http.post<{token:string}>(`${this.apiUrl}/login`,credentials,{
      headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
    });
  }

  isLoggedIn():boolean{
    if(this.isBrowser){
      const token=sessionStorage.getItem("authToken");
      
      return !!token;
    }
    return false;
  }

  logout():void{
    if(this.isBrowser){
      sessionStorage.removeItem('authToken');
      sessionStorage.removeItem('password');
    }
    
  }
  getToken(): string | null {
    if(this.isBrowser){
      return sessionStorage.getItem('authToken');
    }
    return null;
  }

  decodeToken(): any {
    const token = this.getToken();
    return token ? jwtDecode(token) : null;
  }
  getRoles(): string|null {
    const decodedToken = this.decodeToken();
  
    return decodedToken?.roles;
  }
  getName():string|null{
    const decodedToken=this.decodeToken();
    return decodedToken?.sub;
  }
}
