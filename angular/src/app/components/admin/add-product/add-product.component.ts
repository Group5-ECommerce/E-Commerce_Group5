import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/product.model';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {
  product = new Product();
  showAlert = false;
  file?: File;
  constructor(private productService: ProductService) { }

  ngOnInit(): void {
  }

  async saveProduct() {
    await this.uploadImgToCloud();
    this.productService.createProduct(this.product).subscribe((response: any) => {
      this.product = new Product(); //reset
      this.showAlert = true;
    })
  }

  getFile(event: any) {
    this.file = event.target.files[0];
    console.log(this.file)
  }

  uploadImgToCloud() {
    return new Promise<string>((resolve, reject) => {

      if (this.file) {
        this.productService.saveImgToCloudinary(this.file).subscribe((response: any) => {
          console.log(response)
          this.product.productImage = response.secure_url;
          resolve("image cloud api url saved")
        })
      } else { reject('img file null or undefined') }

    })
  }



  closeAlert() {
    this.showAlert = false;
  }

}
