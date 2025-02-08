import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Order } from './profile.service';


export interface Category{
  nev:string
  azonosito:number
}

export interface Product{
  azonosito:number,
  nev:string,
  ar:number,
  lejarati_datum:string,
  kep:string,
  mennyiseg:number,
  kategoria:Category,
  quantity:number,
  rendeles:Order
}

@Injectable({
  providedIn: 'root'
})


export class ProductsService {
  private apiUrl="http://161.35.204.146:8080/api/products";
  constructor(private http:HttpClient) { }

  getCategories():Observable<Category[]>{
    return this.http.get<Category[]>(`${this.apiUrl}/categories`);
  }
  getProducts(search?:string,
    categoryIds?:number[],
    sortBy:string ='id'
  ):Observable<Product[]>{
    let params=new HttpParams();
    if(search){
      params=params.set('search',search);
    }
    if(categoryIds&& categoryIds.length>0){
      params=params.set('categoryIds',categoryIds.join(','));
    }
    params=params.set("sortBy",sortBy);
    
    return this.http.get<Product[]>(`${this.apiUrl}`,{params});
  }
}
