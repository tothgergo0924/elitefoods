import { Component } from '@angular/core';
import { Category, Product, ProductsService } from '../products.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Cart } from '../profile.service';
import { CartService } from '../cart.service';
import { Router } from '@angular/router';
import { NavbarComponent } from '../navbar/navbar.component';
import { AuthServiceService } from '../auth-service.service';


@Component({
  selector: 'app-products',
  standalone: true,
  imports: [CommonModule,FormsModule,NavbarComponent],
  templateUrl: './products.component.html',
  styleUrl: './products.component.css'
})
export class ProductsComponent{
  loading=true;
  loadingproducts=true;
  constructor(private navbar:NavbarComponent,private service:ProductsService,private cartService:CartService,private router:Router,private auth:AuthServiceService){}
  authenticated:boolean=this.auth.isLoggedIn();
  categories:Category[]=[];
  error:string | null=null;
  message:string|null=null;
  sortBy:string='id';
  products:Product[]=[];
  search:string='';
  selectedCategoryIds:number[]=[];
  ngOnInit():void{
    this.authenticated=this.auth.isLoggedIn();
    this.service.getCategories().subscribe({
      next:(data)=>{
        this.categories=data;
        this.loading=false;
      },
      error:(err)=>{
        this.error='Error fetching users!';
        this.loading=false;
      },
    });
    this.loadProducts();
  
  }
 
 
  loadProducts():void{
    this.service.getProducts(this.search,this.selectedCategoryIds,this.sortBy).subscribe({
      next:(data)=>{
        this.products=data;
        this.products= this.products.filter(product=>product.kategoria!==null);
        const cartItems:Cart[]=this.cartService.getCart();
        this.products.forEach(product => {
          const isInCart = cartItems.some(cartItem => cartItem.azonosito === product.azonosito);
          if (!isInCart) {
            product.quantity = 0;
          }else{
            const found=cartItems.find(cart=>cart.azonosito===product.azonosito);
            if(found!==undefined){
              product.quantity=found.mennyiseg;
            }else{
              product.quantity=0;
            }
          
          }

        });

        this.loadingproducts=false;
      },
      error:(error)=>{
        console.log("Error loading products:", error.error);
        this.loadingproducts=false;
      }
    });
    
  }
  onFilterChange():void{
    this.loadProducts();
  }
  onCategoryChange(event: Event,categoryId:number):void{
    const isChecked=(event.target as HTMLInputElement).checked
    if(isChecked){
      this.selectedCategoryIds.push(categoryId);
    }else{
      this.selectedCategoryIds=this.selectedCategoryIds.filter(id=>id!==categoryId);

    }
    this.loadProducts();
  }

  decrementQuantity(product:Product) {
    if(product.quantity>0){
      product.quantity-=1;
    }
  }
  incrementQuantity(product:Product) {
    if(product.quantity<product.mennyiseg){
      product.quantity+=1;
    }
  }
  addToCart(product:Product){
    if(product.quantity===0){
      return;
    }
    const cartItem:Cart={azonosito:product.azonosito,nev:product.nev,kep:product.kep,ar:product.ar,mennyiseg:product.quantity};
    this.cartService.getCart().forEach(cart => {
      if(cart.azonosito===product.azonosito){
        this.cartService.removeFromCart(product.azonosito);
      }
    });

    this.cartService.saveItem(cartItem);
    this.navbar.refresh();
    this.router.navigate(["/cart"])
  }



}
