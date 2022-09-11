import { Component, OnInit } from '@angular/core';
import { Order } from 'src/app/models/order.model';
import { OrderService } from 'src/app/services/order.service';

@Component({
  selector: 'app-my-orders',
  templateUrl: './my-orders.component.html',
  styleUrls: ['./my-orders.component.css']
})
export class MyOrdersComponent implements OnInit {

  constructor(private orderService: OrderService) {}

  orders: Order[];
  pageNum?: number;


  ngOnInit(): void 
  {
    this.pageNum = 1;
    this.viewMyOrders();
  }

  viewMyOrders()
  {
    this.orderService.getMyOrders().subscribe((response) =>
    {
      console.log(response)
      this.orders = response;
      this.orders = this.orders.reverse();
    })
  }

}
