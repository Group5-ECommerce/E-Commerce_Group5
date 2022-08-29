import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/product.model';
import { ProductService } from 'src/app/services/product.service';
import { AddtocartComponent } from 'src/app/addtocart/addtocart.component';

@Component({
  selector: 'app-customer-product-list',
  templateUrl: './customer-product-list.component.html',
  styleUrls: ['./customer-product-list.component.css']
})
export class CustomerProductListComponent implements OnInit {

  products?: Product[];
  currentIndex = -1;
  title = "product list"
  constructor(private productService:ProductService) { }

  ngOnInit(): void {
    this.retrieveProducts();
  }


  retrieveProducts(): void{
    this.productService.getAll().subscribe({
      next: (data)=>{
        this.products=data;
        console.log(data);
      },
      error:(e)=>console.log(e)
    })
  }
}
