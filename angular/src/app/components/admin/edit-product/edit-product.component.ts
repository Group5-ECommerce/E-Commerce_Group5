import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Product } from 'src/app/models/product.model';
import { ProductService } from '../../../services/product.service';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.css']
})
export class EditProductComponent implements OnInit {

  constructor(private route: ActivatedRoute, private productService: ProductService) { }
  id!: number;
  product = new Product();
  showAlert = false;
  file?: File

  ngOnInit() {
    this.id = this.route.snapshot.params.id;

    this.productService.getProduct(this.id).subscribe((response: any) => {
      this.product = response;
    })
  }

  async updateProduct() {
    await this.uploadImgToCloud()
    this.productService.updateProduct(this.product).subscribe((response: any) => {
      console.log(response);
      this.product = new Product();
      this.showAlert = true;
    })
  }

  setFile(event: any) {
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
