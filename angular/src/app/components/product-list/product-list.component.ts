import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/product.model';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {

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
