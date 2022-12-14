import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { OrderItem } from '../models/order-item.model';
import { Order } from '../models/order.model';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private url = environment.backendURL + '/';

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

  getMyOrders(): Observable<any> {
    return this.http.get<Order[]>(this.url + 'myOrders');
  }

  changeOrderStatus(order: Order): Observable<any> {
    return this.http.put<Order>(this.url + "order", order);
  }
}
