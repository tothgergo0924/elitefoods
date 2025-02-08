import { Component } from '@angular/core';
import { ProductcrudService } from '../productcrud.service';
import { Category, Product } from '../products.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { response } from 'express';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-productscrud',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './productscrud.component.html',
  styleUrl: './productscrud.component.css'
})
export class ProductscrudComponent {
  constructor(private service:ProductcrudService){}

  selectedFile:File|null=null;
  message: string | null = null;
  updateMessage:string|null=null;
  products={nev:'',kategoria:{azonosito:1},mennyiseg:'',kep:'',ar:0,lejarati_datum:''};
  loading=true;
  error:string | null=null;
  categories:Category[]=[];
  productsListed:Product[]=[];
  updateError:string|null=null;

  onFileChange(event:any):void{
    if(event.target.files.length>0){
      this.selectedFile=event.target.files[0];
      if(this.selectedFile?.name!==undefined){
        this.products.kep=this.selectedFile?.name;
      }
    
    }
  }

  ngOnInit(){
    this.service.getCategories().subscribe({
      next:(response)=>{
        this.categories=response;
      },error:(error)=>{
        this.error="Error while fetching categories:"+error.error;
      }
        
    });
    this.service.listProducts().subscribe({
      next:(response)=>{
        this.productsListed=response;
        this.loading=false;
      },error:(error)=>{
        this.updateError="Error while fetching products: "+error.error;
      }
    })
  }
  onSubmit(){
      if(this.products.ar===0|| this.products.kep===''||this.products.lejarati_datum===''||this.products.mennyiseg===''||this.products.nev==='' ){
        this.error="You must fill all of the forms correctly!";
      }else{
        this.service.upload(this.selectedFile).subscribe({
          next:(response)=>{
            if(response!=="Error"){
              this.service.add(this.products).subscribe({
                next: (response) => {
                  this.message = 'Product added successfully!'; 
                  this.error=''; 
                  this.service.listProducts().subscribe({
                    next:(response)=>{
                      this.productsListed=response;
                      this.loading=false;
                    },error:(error)=>{
                      this.updateError="Error while fetching products: "+error.error;
                    }
                  })
                },
                error: (error) => {
                  console.error('Error while adding Product: ', error);
                  this.error=error.error;
                  this.message='';
                }
              });
            }
          }
            
        }) 
      }
          
    }
    save(product:Product){
      this.service.editProduct(product,product.azonosito).subscribe({
        next:(response)=>{
        
          this.updateMessage=product.nev+response;
          this.updateError='';
        },error:(error)=>{
          this.updateError='Failed to update the product: '+error.error;
          this.updateMessage='';
        }
      });
    }

    delete(product:Product){
      if(confirm('Are you sure you want to delete this product?')){
        this.service.deleteProduct(product.azonosito).subscribe({
          next:()=>{
            this.productsListed=this.productsListed.filter(productn=>productn.azonosito!==product.azonosito);
            this.updateMessage=product.nev+" deleted succesfully!"
            this.updateError='';
          },error:(error)=>{
            this.updateError="Error while deleting product: "+error.error;
            this.updateMessage='';
          }
        });
      }
      
    }
  }

