import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Product } from './product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) { }
  url = "http://localhost:8080";

  createProduct(product : Product){
    return this.http.post(this.url + "/product", product);
  }

  updateProduct(product : Product){
    return this.http.put(this.url + "/product", product);
  }

  getProductList(){
    return this.http.get(this.url + "/product");
  }

  getProduct(id:number){
    return this.http.get(this.url + "/product/" + id);
  }

  deleteProduct(id:number){
    return this.http.delete(this.url + "/product/" + id);
  }
}
