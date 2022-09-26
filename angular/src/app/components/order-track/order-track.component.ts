import { Component, OnInit, ViewChild } from '@angular/core';
import { OrderItem } from 'src/app/models/order-item.model';
import { Order } from 'src/app/models/order.model';
import { OrderService } from '../../services/order.service'
import { OrderedProductsComponent } from '../ordered-products/ordered-products.component';


@Component({
  selector: 'app-order-track',
  templateUrl: './order-track.component.html',
  styleUrls: ['./order-track.component.css']
})
export class OrderTrackComponent implements OnInit {

  input = new trackingInput();
  @ViewChild(OrderedProductsComponent) orderedProducts: OrderedProductsComponent;
  orderItems: OrderItem[];
  canSubmit: boolean = false;
  order?: Order;
  editStatus = false;

  constructor(private orderService: OrderService) { }

  ngOnInit(): void {
  }

  trackOrder() {
    this.orderService.getOrderByTracking(this.input.trackingNumber).subscribe({
      next: order => {
        this.canSubmit = false;
        const statusEl = document.getElementById("status")!;
        const statusBtnEl = document.getElementById("status-btn")!;
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
        this.orderedProducts.tracker = this.input.trackingNumber;

        this.changeOrderStatusTheme(status!);
        statusEl.classList.remove("invisible");
        statusBtnEl?.classList.remove("invisible");
      },
      error: e => console.log(e)
    });
  }

  reset() {
    this.input.trackingNumber = "";
    document.getElementById("orderInfo")?.classList.add("invisible");
    document.getElementById("status")?.classList.add("invisible");
    document.getElementById("status-btn")?.classList.add("invisible");
  }

  changeOrderStatus() {
    this.orderService.changeOrderStatus(this.order!).subscribe(order => {
      if (order) {
        console.log(order)
        this.order!.orderStatus = order.orderStatus;
        this.editStatus = false;
        this.changeOrderStatusTheme(this.order?.orderStatus!)
      }
    })

  }



  changeOrderStatusTheme(statusText: string) {
    const statusInner = document.getElementById("statusText")!.textContent = statusText;
    const statusEl = document.getElementById("status")!;
    const statusBtnEl = document.getElementById("status-btn")!;
    switch (this.order?.orderStatus) {
      case "Processing":
        statusEl.style.backgroundColor = "LightBlue";
        statusEl.style.color = "#383d41"
        statusBtnEl.style.backgroundColor = "LightBlue";
        statusBtnEl.style.color = "#383d41"
        break;
      case "Fulfilling":
        statusEl.style.backgroundColor = "#0a58ca";
        statusEl.style.color = "#fff";
        statusBtnEl.style.backgroundColor = "#0a58ca";
        statusBtnEl.style.color = "#fff";
        break;
      case "Shipped":
        statusEl.style.backgroundColor = "#fff3cd";
        statusEl.style.color = "#856404"
        statusBtnEl.style.backgroundColor = "#fff3cd";
        statusBtnEl.style.color = "#856404"
        break;
      case "Delivered":
        statusEl.style.backgroundColor = "#d4edda";
        statusEl.style.color = "#155724"
        statusBtnEl.style.backgroundColor = "#d4edda";
        statusBtnEl.style.color = "#155724"
        break;
      case "Cancelled":
        statusEl.style.backgroundColor = "#f8d7da";
        statusEl.style.color = "#721c24"
        statusBtnEl.style.backgroundColor = "#f8d7da";
        statusBtnEl.style.color = "#721c24"
        break;
      default:
        statusEl.style.backgroundColor = "#fefefe";
        statusEl.style.color = "#818182";
        statusBtnEl.style.backgroundColor = "#fefefe";
        statusBtnEl.style.color = "#818182";
        break;
    }
  }

}



class trackingInput {
  trackingNumber: string;
}
