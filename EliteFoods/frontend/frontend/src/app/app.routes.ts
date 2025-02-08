import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ProfileComponent } from './profile/profile.component';
import { ProductsComponent } from './products/products.component';
import { ProductscrudComponent } from './productscrud/productscrud.component';
import { UsercrudComponent } from './usercrud/usercrud.component';
import { CartComponent } from './cart/cart.component';
import { authGuard } from './AuthGuard';
import { NoAuthGuard } from './NoAuthGuard';
import { DetailsComponent } from './details/details.component';

export const routes: Routes = [
    { path: '', component: HomeComponent }, // Alapértelmezett útvonal
      { path: 'home', component: HomeComponent },
      { path:'login',component:LoginComponent,canActivate:[NoAuthGuard]},
      { path:'register',component:RegisterComponent,canActivate:[NoAuthGuard]},
      { path:'profile',component:ProfileComponent,canActivate:[authGuard]},
      { path:'products',component:ProductsComponent},
      { path:'productscrud',component:ProductscrudComponent,canActivate:[authGuard]},
      { path:'usercrud',component:UsercrudComponent,canActivate:[authGuard]},
      { path:'cart',component:CartComponent,canActivate:[authGuard]},
      {path:'details/:id',component:DetailsComponent,canActivate:[authGuard]},
      {path:'**',redirectTo:'/home'}
];
