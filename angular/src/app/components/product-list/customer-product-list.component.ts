import { Component, OnInit } from '@angular/core';
import { Product } from '../../models/product.model';
import { CartService } from 'src/app/services/cart.service';
import { ProductService } from 'src/app/services/product.service';
import { filter, from, map, Observable, of, tap } from 'rxjs';
import { Cart2Service } from 'src/app/services/cart2.service';

@Component({
  selector: 'app-customer-product-list',
  templateUrl: './customer-product-list.component.html',
  styleUrls: ['./customer-product-list.component.css']
})
export class CustomerProductListComponent implements OnInit {

  products?: Product[];
  displayedProducts?: Product[];
  currentIndex = -1;
  title = "product list"
  pageNum?: number
  query?: string
  queryResults?: number

  constructor(private productService: ProductService, private cartService: CartService, private cart2Service: Cart2Service) { }


  ngOnInit(): void {
    this.pageNum = 1

    this.retrieveProducts();
  }
  ngOnDestroy(): void {

  }

  saveToCart(el: HTMLElement, product: Product): void {
    // If it is a primary button, meaning it should "Add To Cart"
    if (el.classList.contains("btn-primary")) {
      // this.cartService.addProduct(product);
      this.cart2Service.addProduct(product)
      el.classList.remove("btn-primary");
      el.classList.add("btn-danger");
      el.textContent = "Remove From Cart";
    }
    else {
      // this.cartService.removeProduct(product);
      this.cart2Service.deleteProduct(product)
      el.textContent = "Add to Cart";
      el.classList.remove("btn-danger");
      el.classList.add("btn-primary");
    }

  }

  // saveToIndexedDb(product: Product) {
  //   this.cart2Service.addProduct(product)
  // }



  retrieveProducts(): void {
    this.productService.getProductList().subscribe({
      next: (data) => {
        this.products = (data);
        this.displayedProducts = this.products;
        this.queryResults = this.displayedProducts.length;
      },
      error: (e) => console.log(e)
    })
  }

  showResults() {
    // if (this.query) {
    //   this.displayedProducts = this.products?.pipe(
    //     map((prods: any[]) => {
    //       return prods.filter((p: Product) => {
    //         if (p.productName) {
    //           console.log(p.productName, "===", this.query, " result: ", p.productName === this.query)
    //           return p.productName.toLowerCase() == this.query?.toLowerCase() || this.query === ""
    //         }
    //         return false
    //       })
    //     })
    //   )

    // }
    if (this.query) {
      this.displayedProducts = this.products?.filter(p => {
        // console.log("query ", this.query)
        if (p.productName) {
          return p.productName.toLowerCase() == this.query?.toLowerCase()
            || p.productName.toLowerCase().indexOf(this.query!.toLowerCase()) !== -1
        }
        return false
      })
      this.queryResults = this.displayedProducts?.length;
    } else {
      this.displayedProducts = this.products
      this.queryResults = this.displayedProducts?.length
    }
  }

  resetResults() {
    this.displayedProducts = this.products
    this.queryResults = this.displayedProducts?.length
  }

  // tester(event: any) {
  //   console.log((event.target.value).toLocalLowerCase())
  // }


}
