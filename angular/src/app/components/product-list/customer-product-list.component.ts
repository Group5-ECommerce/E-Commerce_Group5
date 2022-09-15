import { Component, OnInit } from '@angular/core';
import { Product } from '../../models/product.model';
import { CartService } from 'src/app/services/cart.service';
import { ProductService } from 'src/app/services/product.service';
import { filter, Observable } from 'rxjs';

interface ProductCategory {
  code:string;
  description:string;
}

@Component({
  selector: 'app-customer-product-list',
  templateUrl: './customer-product-list.component.html',
  styleUrls: ['./customer-product-list.component.css']
})

export class CustomerProductListComponent implements OnInit {

  products?: Product[];
  currentIndex = -1;
  title = "product list"
  pageNum?: number
  selectedCategory?: string[];

  constructor(private productService: ProductService, private cartService: CartService) { }


  ngOnInit(): void {
    this.pageNum = 1

    this.retrieveProducts();
  }

  saveToCart(el: HTMLElement, product: Product): void {
    // If it is a primary button, meaning it should "Add To Cart"
    if (el.classList.contains("btn-primary")) {
      this.cartService.addProduct(product);
      el.classList.remove("btn-primary");
      el.classList.add("btn-danger");
      el.textContent = "Remove From Cart";
    }
    else {
      this.cartService.removeProduct(product);
      el.textContent = "Add to Cart";
      el.classList.remove("btn-danger");
      el.classList.add("btn-primary");
    }

  }



  retrieveProducts(): void {
    this.productService.getProductList().subscribe({
      next: (data) => {
        this.products = data;
        this.selectedCategory = this.products.map(p => p.category)
        console.log(this.selectedCategory);
      },
      error: (e) => console.log(e)
    })
  }
}
