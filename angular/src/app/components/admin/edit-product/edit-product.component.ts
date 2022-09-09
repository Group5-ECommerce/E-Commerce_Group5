import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
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
  fileUploaded = false;
  file?: File;
  //only init right before AfterViewInit
  @ViewChild('fileInput')
  fileUploadElem: ElementRef;
  //for form reset
  @ViewChild('editProductForm')
  form: NgForm

  useExistingImg?: boolean

  ngOnInit() {
    this.id = this.route.snapshot.params.id;

    this.productService.getProduct(this.id).subscribe((response: any) => {
      this.product = response;
    })

    this.useExistingImg = true;
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

  updateProduct(checkboxEl: any) {
    this.uploadImgToCloud().then(
      (result) => {
        this.productService.updateProduct(this.product).subscribe((response: any) => {
          // this.product = new Product();
          this.showAlert = true;
          this.form.resetForm(this.form.value);
          this.fileUploaded = false;
          this.useExistingImg = true;
          checkboxEl.checked = true;
        })
      },
      (error) => { console.log(error) }
    )

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
