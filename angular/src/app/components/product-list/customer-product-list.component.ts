import { Component, OnInit } from '@angular/core';
import { Product } from '../../models/product.model';
import { ProductService } from 'src/app/services/product.service';
import { AddtocartserviceService } from 'src/app/services/addtocartservice.service';

@Component({
  selector: 'app-customer-product-list',
  templateUrl: './customer-product-list.component.html',
  styleUrls: ['./customer-product-list.component.css']
})
export class CustomerProductListComponent implements OnInit {

  products?: Product[];
  currentIndex = -1;
  title = "product list"
  constructor(private productService:ProductService, private addtocartservice:AddtocartserviceService) { }

  ngOnInit(): void {
    this.retrieveProducts();
  }

  saveToCart(el:HTMLElement, product: Product): void
  {
    this.addtocartservice.addProduct(product);
    if (el.classList.contains("btn-primary")){
      el.classList.remove("btn-primary");
      el.classList.add("btn-danger");
      el.textContent = "Remove From Cart";
    }
    else {
      el.textContent = "Add to Cart";
      el.classList.remove("btn-danger");
      el.classList.add("btn-primary");
    }

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
