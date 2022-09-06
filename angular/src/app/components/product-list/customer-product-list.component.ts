import { Component, OnInit } from '@angular/core';
import { Product } from '../../models/product.model';
import { CartService } from 'src/app/services/cart.service';
import { ProductService } from 'src/app/services/product.service';
import { filter, from, map, Observable, of, tap } from 'rxjs';

@Component({
  selector: 'app-customer-product-list',
  templateUrl: './customer-product-list.component.html',
  styleUrls: ['./customer-product-list.component.css']
})
export class CustomerProductListComponent implements OnInit {

  products?: Observable<Product[]>;
  displayedProducts?: Observable<Product[]>;
  currentIndex = -1;
  title = "product list"
  pageNum?: number
  query?: string

  constructor(private productService: ProductService, private cartService: CartService) { }


  ngOnInit(): void {
    this.pageNum = 1

    this.retrieveProducts();
    console.log(this.products)
  }
  ngOnDestroy(): void {

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
        this.products = of(data);
        this.displayedProducts = this.products;
        console.log(data)
      },
      error: (e) => console.log(e)
    })
  }

  showResults() {
    if (this.query) {
      this.displayedProducts = this.products?.pipe(
        map((prods: any[]) => {
          return prods.filter((p: Product) => {
            if (p.productName) {
              console.log(p.productName, "===", this.query, " result: ", p.productName === this.query)
              return p.productName.toLowerCase() == this.query?.toLowerCase() || this.query === ""
            }
            return false
          })
        })
      )

    }
  }


}
