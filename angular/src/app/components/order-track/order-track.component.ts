import { Component, OnInit } from '@angular/core';
import {OrderService} from '../../services/order.service'


@Component({
  selector: 'app-order-track',
  templateUrl: './order-track.component.html',
  styleUrls: ['./order-track.component.css']
})
export class OrderTrackComponent implements OnInit {

  input = new trackingInput();

  constructor(private orderService: OrderService) { }

  ngOnInit(): void {
  }

  trackOrder(){
    this.orderService.getOrderByTracking(this.input.trackingNumber).subscribe(order => {
      console.log(order);
    });
  }

}

class trackingInput 
{
    trackingNumber : string;
}
