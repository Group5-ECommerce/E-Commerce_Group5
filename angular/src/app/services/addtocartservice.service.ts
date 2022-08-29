import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../model/product';

const baseUrl = "http://localhost:8080/cart";
@Injectable({
  providedIn: 'root'
})
export class AddtocartserviceService {

  constructor(private http: HttpClient) { }

  addProduct(data: Product): Observable<Product>{
      const postUrl = baseUrl + "/" + data.productId + "/1";
      return this.http.post(postUrl, data);
  }
}
