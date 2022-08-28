import { Component, OnInit } from '@angular/core';
import { Product } from '../product/product';
import { ProductService } from '../product/product.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.css']
})
export class ProductListComponent implements OnInit {
  products!: Product[];
  
  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.productService.getProductList().subscribe((response: any) => {
      this.products = response;
    });
  }

  deleteProduct(id: any){
    this.productService.deleteProduct(id).subscribe((response) => {
      console.log(response);
      this.products=this.products.filter((p: any) => {
        return id!= p.id;
      })
    })
  }

}
