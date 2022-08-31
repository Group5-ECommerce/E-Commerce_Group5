import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PaymentInfo } from '../common/paymentInfo/payment-info';
import { Purchase } from '../common/purchase/purchase';

@Injectable({
  providedIn: 'root'
})
export class CheckoutService {

 private checkOutUrl = 'http://localhost:8080/checkout'

  constructor(private HttpClient: HttpClient) { }

  confirmOrder(data: any, email: any): Observable<any>
  {
  
    return this.HttpClient.post<Purchase>(`${this.checkOutUrl}/${email}`,data);
  }
}
