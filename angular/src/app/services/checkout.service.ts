import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Payment } from '../models/payment';
import { Purchase } from '../models/purchase/purchase';
@Injectable({
  providedIn: 'root'
})
export class CheckoutService {

  private checkOutUrl = 'http://localhost:8080/checkout'

  private paymentIntentUrl = 'http://localhost:8080/payment-intent'

  constructor(private HttpClient: HttpClient) { }
  
  confirmOrder(data: any, email: any, name: any): Observable<any>
  {
    return this.HttpClient.post<Purchase>(`${this.checkOutUrl}/${email}/${name}`,data);
  }

  createPaymentIntent(data: any): Observable<any>
  {
    return this.HttpClient.post<Payment>(`${this.paymentIntentUrl}`,data)
  }
}
