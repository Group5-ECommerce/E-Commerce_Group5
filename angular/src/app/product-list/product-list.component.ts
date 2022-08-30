import { Component, OnInit } from '@angular/core';
<<<<<<< Updated upstream:angular/src/app/product-list/product-list.component.ts
import { Product } from '../product/product';
import { ProductService } from '../product/product.service';
=======
import { Router } from '@angular/router';
import { Product } from '../../../services/product/product';
import { ProductService } from '../../../services/product/product.service';
>>>>>>> Stashed changes:angular/src/app/components/admin/product-list/admin-product-list.component.ts

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {
  products!: Product[];
  
  constructor(private productService: ProductService, private router: Router) { 
    this.router.routeReuseStrategy.shouldReuseRoute = () => {
      return false;
    };
  }

  ngOnInit(): void {
    this.productService.getProductList().subscribe((response: any) => {
      this.products = response;
    });
  }

  deleteProduct(id: any){
    this.productService.deleteProduct(id).subscribe((response) => {
      console.log(response);
      this.router.navigateByUrl('/product-list');
    })
  }

}
