import { Component, OnInit } from '@angular/core';
import { Product } from '../../models/product.model';
import { ProductService } from 'src/app/services/product.service';
import { IndexCartService } from 'src/app/services/index-cart.service';
import { filter, map, Observable } from 'rxjs';
import { OktaAuthStateService } from '@okta/okta-angular';
import { AuthState } from '@okta/okta-auth-js';

interface ProductCategory {
  code: string;
  description: string;
}


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
  selectedCategory?: string[];
  filteredCategory?= ''
  isAdmin$: Observable<boolean>;

  public ChangeCategory($event: any) {
    this.filteredCategory = ($event.target as HTMLSelectElement).value;
  }
  query?: string
  queryResults?: number

  constructor(private productService: ProductService, private indexCartService: IndexCartService, private _oktaStateService: OktaAuthStateService) { }


  ngOnInit(): void {
    this.pageNum = 1
    this.retrieveProducts();

    this.isAdmin$ = this._oktaStateService.authState$.pipe(
      filter((authState: AuthState) => !!authState && !!authState.isAuthenticated),
      map((authState: any) => authState.idToken?.claims.groups.includes("Admin") ?? false)
    );
  }
  ngOnDestroy(): void {
  }

  saveToCart(el: HTMLElement, product: Product): void {
    // If it is a primary button, meaning it should "Add To Cart"
    if (el.classList.contains("btn-primary")) {
      // this.cartService.addProduct(product);
      this.indexCartService.addProduct(product)
      el.classList.remove("btn-primary");
      el.classList.add("btn-danger");
      el.textContent = "Remove From Cart";
    }
    else {
      // this.cartService.removeProduct(product);
      this.indexCartService.deleteProduct(product)
      el.textContent = "Add to Cart";
      el.classList.remove("btn-danger");
      el.classList.add("btn-primary");
    }

  }

  retrieveProducts(): void {
    console.log(this.filteredCategory);
    this.productService.getProductList().subscribe({
      next: (data) => {
        this.products = (data);
        this.displayedProducts = this.products;
        this.queryResults = this.displayedProducts.length;
        this.selectedCategory = [...new Set(this.products.map(p => p.category))]
      },
      error: (e) => console.log(e)
    })
  }

  showResults() {
    this.pageNum = 1;
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
    if (this.filteredCategory != '')
      this.displayedProducts = this.displayedProducts?.filter(p => p.category === this.filteredCategory)
  }

  resetResults() {
    this.displayedProducts = this.products
    // Resets category dropdown to "All products"
    document.getElementById("categoryDropdown")!.getElementsByTagName('option')[0].selected = true;
    this.filteredCategory = "";

    this.queryResults = this.displayedProducts?.length
  }
}
