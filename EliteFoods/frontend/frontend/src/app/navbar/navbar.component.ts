import { Component } from '@angular/core';
import { Router, RouterLink, RouterModule, RouterOutlet } from '@angular/router';
import { FooterComponent } from '../footer/footer.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthServiceService } from '../auth-service.service';
import { CartService } from '../cart.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterModule,FooterComponent,CommonModule,FormsModule],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  constructor(private auth:AuthServiceService,private router:Router,private cartService:CartService) {
  
  }
  currentRoute:string='';
  cart:number=0;
  onSubmit(){
    this.auth.logout();
    this.router.navigate(["/login"]);
    

  }
  get logged():boolean{
  
    return this.auth.isLoggedIn();
  }
  get admin():boolean{
    return this.auth.getRoles()==="ROLE_ADMIN";
  }
  ngOnInit(){
    this.cart=this.cartService.getCart().length;
    this.currentRoute=this.router.url;
  }
  isActive(route:string):boolean{
    return this.currentRoute.includes(route);
  }
  toggleNav(): void {
    const navLinks = document.getElementById('nav-links');
    if (navLinks) {
      navLinks.classList.toggle('show');
    }
  }
  

  refresh(){
    this.cart=this.cartService.getCart().length;

  }


}
