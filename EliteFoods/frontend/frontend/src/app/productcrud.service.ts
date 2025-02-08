import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Category, Product } from './products.service';
import e from 'express';



@Injectable({
  providedIn: 'root'
})



export class ProductcrudService {
  private apiUrl="http://161.35.204.146:8080/api/productcrud";
    constructor(private http:HttpClient) { }

  getCategories():Observable<Category[]>{
    return this.http.get<Category[]>(`${this.apiUrl}/categories`);
  }
  add(product:{nev:string,kategoria:{azonosito:number},mennyiseg:string,kep:string,ar:number,lejarati_datum:string})
    :Observable<string>{
      if(product!==null){
        return this.http.post(`${this.apiUrl}/add`,product,{headers: new HttpHeaders({
          'Content-Type': 'application/json'
        }),
        responseType: 'text' // szimpla szöveg válasz
        });
      }
      return new Observable(ob=>{
        ob.next("Null cannot be added!");
        ob.complete();
      });
      
  }
  upload(selectedFile:File|null,):Observable<string>{
    if(selectedFile){
      const formData=new FormData();
      formData.append('file',selectedFile,selectedFile.name);
      return this.http.post('http://161.35.204.146:8080/api/upload',formData,{headers: new HttpHeaders({
      }),
      responseType: 'text' // szimpla szöveg válasz
    });
    
      
  
  
  
    }
    return new Observable(obs=>{
      obs.next("Error");
      obs.complete();
    })
  }

  listProducts():Observable<Product[]>{
    return this.http.get<Product[]>(this.apiUrl);
  }
  editProduct(product:Product,id:number):Observable<string>{
    return this.http.put<string>(`${this.apiUrl}/edit/${id}`,product,{ responseType: 'text' as 'json' } );
  }
  deleteProduct(id:number):Observable<void>{
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`);
  }

}
