import { Component, OnInit } from '@angular/core';
import { Order } from 'src/app/models/order.model';
import { OrderService } from 'src/app/services/order.service';

@Component({
  selector: 'app-order-list',
  templateUrl: './order-list.component.html',
  styleUrls: ['./order-list.component.css']
})
export class OrderListComponent implements OnInit {

  constructor(private orderService: OrderService) { }

  orders: Order[];
  pageNum?: number;

  ngOnInit(): void {
    this.pageNum = 1
    this.viewOrders()
  }

  viewOrders() {
    this.orderService.getOrders().subscribe((response) => {
      console.log(response)
      this.orders = response;
    })
  }


}
