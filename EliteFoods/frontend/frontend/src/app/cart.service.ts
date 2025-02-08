import { Injectable, Inject, PLATFORM_ID } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Cart } from './profile.service';
import { isPlatformBrowser } from '@angular/common';
import { Observable } from 'rxjs';
import { NavbarComponent } from './navbar/navbar.component';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private apiUrl="http://161.35.204.146:8080/api/cart";
  constructor(private http: HttpClient, @Inject(PLATFORM_ID) private platformId: Object) { }

  saveItem(item: Cart): void {
    if (isPlatformBrowser(this.platformId)) {
      const cart = this.getCart(); // Lévő kosár elemek kiolvasása
      cart.push(item); // Új elem hozzáadása
      sessionStorage.setItem('cart', JSON.stringify(cart));
    }
  }

  getCart(): Cart[] {
    if (isPlatformBrowser(this.platformId)) {
      const storedCart = sessionStorage.getItem('cart'); // Kosár kiolvasása
      return storedCart ? JSON.parse(storedCart) : []; // Ha nincs, akkor üres tömb
    }
    return [];
  }



  removeFromCart(itemId: number): void {
    if (isPlatformBrowser(this.platformId)) {
      const cart = this.getCart(); // Lévő kosár elemek kiolvasása
      const updatedCart = cart.filter(item => item.azonosito !== itemId); // Elem eltávolítása
      sessionStorage.setItem('cart', JSON.stringify(updatedCart)); // Frissített kosár mentése
    }
  }

  clearCart(): void {
    if (isPlatformBrowser(this.platformId)) {
      sessionStorage.removeItem('cart'); // Kosár teljes törlése
    }
    
  }
  purchase(cartItems:Cart[],username:string,price:number):Observable<string>{
    const purchaseData = {
      products: cartItems,
      username: username,
      price:price
    };
    
    return this.http.post(this.apiUrl, purchaseData, {
      responseType: 'text' // Beállítjuk, hogy a válasz típusa szöveg legyen
    });  }
}
