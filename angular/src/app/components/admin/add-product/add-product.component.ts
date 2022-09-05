import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
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
  fileUploaded = false;
  file?: File;
  //only init right before AfterViewInit
  @ViewChild('fileInput')
  fileUploadElem: ElementRef;

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.fileUploaded = false;
  }

  async saveProduct() {
    await this.uploadImgToCloud();
    this.fileUploaded = false;
    this.fileUploadElem.nativeElement.value = "";
    this.productService.createProduct(this.product).subscribe((response: any) => {
      this.product = new Product(); //reset
      this.showAlert = true;
    })
  }

  fileUpload(event: any) {
    if (event.target.files.length > 0) {
      this.fileUploaded = true;
      this.file = event.target.files[0];
    } else {
      this.fileUploaded = false;
    }
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
