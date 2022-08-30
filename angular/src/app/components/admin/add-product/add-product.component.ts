import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/product.model';
import { ProductService } from '../../../services/product.service';

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

  saveProduct() {
    this.productService.createProduct(this.product).subscribe((response) => {
      console.log(response);
      this.product = new Product(); //reset
      this.showAlert = true;
    })
  }

  uploadImgToCloud(event: any) {
    this.productService.saveImgToCloudinary(event.target.files[0]).subscribe((response: any) => {
      this.product.productImage = response.secure_url;
    })

  }

  closeAlert() {
    this.showAlert = false;
  }

}
