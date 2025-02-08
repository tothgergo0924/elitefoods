import { Component } from '@angular/core';
import { Order, ProfileService } from '../profile.service';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-details',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './details.component.html',
  styleUrl: './details.component.css'
})
export class DetailsComponent {
  constructor(private profileService:ProfileService,private router:Router,private route:ActivatedRoute) {
    
  }
  order:Order|null=null;
  error:string='';
  ngOnInit(){
    const orderId = this.route.snapshot.paramMap.get('id');
    if(orderId!==null){
      this.profileService.getOrderById(+orderId).subscribe({
        next:(response)=>{
          this.order=response;
        },
        error:(error)=>{
          error=error.error;
        }
      });
    }
    
  }
}
