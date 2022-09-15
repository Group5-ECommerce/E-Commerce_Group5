import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Purchase } from '../models/purchase/purchase';
@Injectable({
  providedIn: 'root'
})
export class CheckoutService {

  private checkOutUrl = 'http://localhost:8000/checkout'

  constructor(private HttpClient: HttpClient) { }
  
  confirmOrder(data: any, email: any, name: any): Observable<any>
  {
    return this.HttpClient.post<Purchase>(`${this.checkOutUrl}/${email}/${name}`,data);
  }
}
