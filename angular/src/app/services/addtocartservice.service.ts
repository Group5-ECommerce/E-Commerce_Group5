import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../model/product';

const baseurl = "http://localhost:8080/cart";
@Injectable({
  providedIn: 'root'
})
export class AddtocartserviceService {

  constructor(private http: HttpClient) { }

  addProduct(data: Product): Observable<Product>{
      return this.http.post('${baseurl}/${id}/${amt}', data);
  }
}
