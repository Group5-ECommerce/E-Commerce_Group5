import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { OrderItem } from '../models/order-item.model';
import { Order } from '../models/order.model';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private url = "http://localhost:8080/"

  constructor(private http: HttpClient) { }

  //view orders
  getOrders(): Observable<any> {
    return this.http.get<Order[]>(this.url + 'order');
  }
  //will only get products, not order id (investigate why??)
  getOrderItemsByTracking(tracker?: string) {
    return this.http.get<OrderItem[]>(this.url + `orderItems/${tracker}`)
  }

  getOrderByTracking(tracker?: string) {
    return this.http.get<Order>(this.url + `order/${tracker}`);
  }
}
