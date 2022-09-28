import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
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
  //for form reset
  @ViewChild('addProductForm')
  form: NgForm
  categories: string[];
  makeNewCategory?: boolean;

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.fileUploaded = false;
    this.categories = this.productService.categories;
  }

  async saveProduct() {
    await this.uploadImgToCloud();

    this.productService.createProduct(this.product).subscribe((response: any) => {
      this.product = new Product(); //reset obj
      this.showAlert = true;
      this.form.resetForm(); //reset validators
      this.fileUploaded = false; //reset input file validation and value
      this.fileUploadElem.nativeElement.value = "";
    })
  }

  //on change
  fileUpload(event: any) {
    if (event.target.files.length > 0) {
      this.fileUploaded = true;
      this.file = event.target.files[0];
    } else {
      this.fileUploaded = false; //validation reset
    }
  }

  uploadImgToCloud() {
    return new Promise<string>((resolve, reject) => {

      if (this.file) {
        this.productService.saveImgToCloudinary(this.file).subscribe((response: any) => {

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
