import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map, Observable, Subject } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Product } from '../models/product.model';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }
  url = environment.backendURL;
  private cloudinaryImgSaveUrl = "https://api.cloudinary.com/v1_1/gaurav-cloudinary/image/upload";
  categories: string[];

  createProduct(product: Product) {
    return this.http.post(this.url + "/product", product);
  }

  updateProduct(product: Product) {
    return this.http.put(this.url + "/product", product);
  }

  getProductList() {
    return this.http.get<Product[]>(this.url + "/product");
  }

  getProduct(id: number) {
    return this.http.get(this.url + "/product/" + id);
  }

  deleteProduct(id: number) {
    return this.http.delete(this.url + "/product/" + id);
  }

  rateProduct(productId: number, rating: number) {
    const ratingUrl = this.url + `/rateProduct/${productId}?rating=${rating}`;
    return this.http.post(ratingUrl, null);
  }

  saveImgToCloudinary(file: any) {
    const imgForm = new FormData();
    imgForm.append("file", file)
    imgForm.append("upload_preset", "default_preset")
    imgForm.append("cloud_name", "gaurav-cloudinary")
    return this.http.post(this.cloudinaryImgSaveUrl, imgForm)
  }

  setCategories(cats:string[]){
    this.categories = cats;
  }
}
