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
  file: File | undefined
  useExistingImg?: boolean

  ngOnInit() {
    this.id = this.route.snapshot.params.id;

    this.productService.getProduct(this.id).subscribe((response: any) => {
      this.product = response;
    })

    this.useExistingImg = true;
  }

  // async updateProduct() {
  //   await this.uploadImgToCloud().catch((err) => console.log(err))

  //   //still allows this part execution after err
  //   this.productService.updateProduct(this.product).subscribe((response: any) => {
  //     console.log(response);
  //     this.product = new Product();
  //     this.showAlert = true;
  //   })
  // }
  updateProduct() {
    this.uploadImgToCloud().then(
      (result) => {
        this.productService.updateProduct(this.product).subscribe((response: any) => {
          this.product = new Product();
          this.showAlert = true;
        })
      },
      (error) => { console.log(error) }
    )

  }

  setFile(event: any) {
    this.file = event.target.files[0];
  }

  uploadImgToCloud() {
    return new Promise<string>((resolve, reject) => {
      //has to be else-if or reject statement fires despite returning from resolve
      if (this.file) {
        this.productService.saveImgToCloudinary(this.file).subscribe((response: any) => {
          console.log(response)
          this.product.productImage = response.secure_url;
          resolve("image cloud api url saved")
        })
      }
      else if (this.file === undefined && this.useExistingImg) {
        resolve('use existing img for edit')
      } else {
        reject('img file null and useExisingImg is false')
      }


    })
  }

  closeAlert() {
    this.showAlert = false;
  }
}
