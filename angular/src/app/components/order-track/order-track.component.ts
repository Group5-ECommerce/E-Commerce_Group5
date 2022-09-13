import { Component, OnInit, ViewChild } from '@angular/core';
import { OrderItem } from 'src/app/models/order-item.model';
import { Order } from 'src/app/models/order.model';
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
  canSubmit: boolean = false;
  order?: Order;

  constructor(private orderService: OrderService) { }

  ngOnInit(): void {
  }

  trackOrder() {
    this.orderService.getOrderByTracking(this.input.trackingNumber).subscribe({
      next: order => {
        this.canSubmit = false;
        const statusEl = document.getElementById("status")!;
        if (!order) {
          console.log("empty order");
          const statusText = document.getElementById("statusText")!.textContent = "Order not found- try a different tracking number.";
          statusEl.style.backgroundColor = "#e1cc8a";
          statusEl.style.color = "#4e3c08";
          statusEl.classList.remove("invisible");
          return;
        }
        this.order = order;
        document.getElementById("orderInfo")?.classList.remove("invisible");

        this.orderItems = order.items;
        const status = order.orderStatus;
        document.getElementById("")

        const statusText = document.getElementById("statusText")!.textContent = order.orderStatus!;
        switch (status) {
          case "Processing":
            statusEl.style.backgroundColor = "LightBlue";
            statusEl.style.color = "#383d41"
            break;
          case "Fulfilling":
            statusEl.style.backgroundColor = "#0a58ca";
            statusEl.style.color = "#fff"
            break;
          case "Shipped":
            statusEl.style.backgroundColor = "#fff3cd";
            statusEl.style.color = "#856404"
            break;
          case "Delivered":
            statusEl.style.backgroundColor = "#d4edda";
            statusEl.style.color = "#155724"
            break;
          case "Cancelled":
            statusEl.style.backgroundColor = "#f8d7da";
            statusEl.style.color = "#721c24"
            break;
          default:
            statusEl.style.backgroundColor = "#fefefe";
            statusEl.style.color = "#818182";
            break;
        }
        statusEl.classList.remove("invisible");
      },
      error: e => console.log(e)
    });
  }

  reset() {
    this.input.trackingNumber = "";
    document.getElementById("orderInfo")?.classList.add("invisible");
    document.getElementById("status")?.classList.add("invisible");
  }
}

class trackingInput {
  trackingNumber: string;
}
