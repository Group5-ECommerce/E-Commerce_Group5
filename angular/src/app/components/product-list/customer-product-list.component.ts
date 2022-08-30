import { Component, OnInit } from '@angular/core';
import { Product } from '../../models/product.model';
import { CartService } from 'src/app/services/cart.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-customer-product-list',
  templateUrl: './customer-product-list.component.html',
  styleUrls: ['./customer-product-list.component.css']
})
export class CustomerProductListComponent implements OnInit {

  products?: Product[];
  currentIndex = -1;
  title = "product list"
  constructor(private productService: ProductService, private cartService: CartService) { }


  ngOnInit(): void {
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
        console.log(data);
      },
      error: (e) => console.log(e)
    })
  }
}
