import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/product.model';
import { ProductService } from '../../../services/product.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin-product-list',
  templateUrl: './admin-product-list.component.html',
  styleUrls: ['./admin-product-list.component.css']
})

export class AdminProductListComponent implements OnInit {
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

  deleteProduct(id: any) {
    this.productService.deleteProduct(id).subscribe((response) => {
      console.log(response);
      this.products = this.products.filter((p: any) => {
        return id != p.id;
      })
    })
  }

}
