import { Component, OnInit, ViewChild } from '@angular/core';
import { OrderItem } from 'src/app/models/order-item.model';
import { OrderService } from '../../services/order.service'
import { OrdereredProductsComponent } from '../orderered-products/orderered-products.component';


@Component({
  selector: 'app-order-track',
  templateUrl: './order-track.component.html',
  styleUrls: ['./order-track.component.css']
})
export class OrderTrackComponent implements OnInit {

  input = new trackingInput();
  @ViewChild(OrdereredProductsComponent) orderedProducts: OrdereredProductsComponent;
  orderItems: OrderItem[];

  constructor(private orderService: OrderService) { }

  ngOnInit(): void {
  }

  trackOrder() {
    this.orderService.getOrderByTracking(this.input.trackingNumber).subscribe({
      next: order => {
        document.getElementById("trackingOrderItems")?.classList.toggle("invisible");
        this.orderItems = order.items;
        const status = order.orderStatus;
        const statusEl = document.getElementById("status")!;
        statusEl.classList.toggle("invisible");
        document.getElementById("")
        const statusText = document.getElementById("statusText")!.textContent = order.orderStatus!;
        switch (status){
          case "Processing":
            statusEl.classList.remove("alert");
            statusEl.classList.add("alert-primary");
            break;
          case "Shipped":
            statusEl.classList.add("alert-info");
            break;
          case "Delivered":
            statusEl.classList.add("alert-success");
            break;
          case "Cancelled":
            statusEl.classList.add("alert-danger");
            break;
          default:
            statusEl.classList.add("alert-warning");
            break;
        }
      },
      error: e => console.log(e)
    });
  }

}

class trackingInput {
  trackingNumber: string;
}
