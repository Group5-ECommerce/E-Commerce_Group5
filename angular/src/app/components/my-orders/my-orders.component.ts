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

  getStatusStyle(status:string) {
    let style = {};
    switch (status) {
      case "Processing":
        style = {
        'backgroundColor': "LightBlue",
        'color':  "#383d41"
        }
        break;
      case "Fulfilling":
        style =  {
          'backgroundColor': "#0a58ca",
          'color':  "#fff"
          }
        break;
      case "Shipped":
        style =  {
          'backgroundColor': "#fff3cd",
          'color':  "#856404"
          }
        break;
      case "Delivered":
        style =  {
          'backgroundColor': "#d4edda",
          'color':  "#155724"
          }
        break;
      case "Cancelled":
        style =  {
          'backgroundColor': "#f8d7da",
          'color':  "#721c24"
          }
        break;
      default:
        style =  {
          'backgroundColor': "#fefefe",
          'color':  "#818182"
          }
        break;
    }
    return style;
  }

}
