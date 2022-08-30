import { Component, OnInit } from '@angular/core';
import { Product } from '../../../services/product/product';
import { ProductService } from '../../../services/product/product.service';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {
  product = new Product();
  showAlert = false;

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
  }

  saveProduct(){
    this.productService.createProduct(this.product).subscribe((response) =>{
      console.log(response);
      this.product = new Product();
      this.showAlert = true;
    })
  }

  closeAlert(){
    this.showAlert = false;
  }

}
