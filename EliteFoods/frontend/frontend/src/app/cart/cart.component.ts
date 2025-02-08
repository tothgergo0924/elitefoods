import { Component } from '@angular/core';
import { CartService } from '../cart.service';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Cart } from '../profile.service';
import { AuthServiceService } from '../auth-service.service';
import { response } from 'express';
import { NavbarComponent } from '../navbar/navbar.component';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [CommonModule,FormsModule,ReactiveFormsModule,NavbarComponent],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css'
})
export class CartComponent {
  cartItems:Cart[]=[];
  checkoutForm:FormGroup=this.fb.group({});
  successMessageVisible: boolean = false;
  error:string='';
  total:number=0;
  constructor(private Navbar:NavbarComponent,private service:CartService,private fb: FormBuilder,private authService:AuthServiceService){}
  ngOnInit(){
    this.cartItems=this.service.getCart();
    this.total=0;
    this.cartItems.forEach(e=>{
      this.total+=(e.ar*e.mennyiseg);
    });
    this.checkoutForm = this.fb.group({
      cardNumber: ['', [Validators.required, Validators.pattern(/^\d{4}-\d{4}-\d{4}-\d{4}$/)]],
      nameOnCard: ['', [Validators.required, Validators.pattern(/^[A-Za-zÁÉÍÓÖŐÚÜŰáéíóöőúüű\s]+$/)]],
      expiryDate: ['', [Validators.required, Validators.pattern(/^(0[1-9]|1[0-2])\/\d{2}$/)]],
      cvc: ['', [Validators.required, Validators.pattern(/^\d{3}$/)]]
    });
    this.Navbar.refresh();
  }

  remove(cartItem:Cart){
    this.service.removeFromCart(cartItem.azonosito);
    this.cartItems=this.service.getCart();
    this.total=0;
    this.cartItems.forEach(e=>{
      this.total+=e.ar;
    });
    this.Navbar.refresh();
  }

  message:string='';
  onSubmit(): void {
    if (this.checkoutForm.valid) {
      
      const username=this.authService.getName();
      if(username!==null){
        this.service.purchase(this.cartItems,username,this.total).subscribe({
          next:(response)=>{
            this.message=response;
            this.error='';
            this.service.clearCart();
            this.successMessageVisible = true;
            this.checkoutForm.reset();
            this.cartItems=[];
            this.total=0;
            this.Navbar.refresh();
          },error:(error)=>{
            this.error="Error while purchase:"+error.error;
            this.message='';
            
          }
        })
      }
      
    } else {
      alert('Please fill in all the fields correctly.');
    }
  }
  get cardNumber() { return this.checkoutForm.get('cardNumber'); }
  get nameOnCard() { return this.checkoutForm.get('nameOnCard'); }
  get expiryDate() { return this.checkoutForm.get('expiryDate'); }
  get cvc() { return this.checkoutForm.get('cvc'); }
}
