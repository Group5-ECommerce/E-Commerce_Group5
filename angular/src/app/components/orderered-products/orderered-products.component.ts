import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { OrderItem } from 'src/app/models/order-item.model';
import { Product } from 'src/app/models/product.model';
import { OrderService } from 'src/app/services/order.service';

@Component({
  selector: 'app-orderered-products',
  templateUrl: './orderered-products.component.html',
  styleUrls: ['./orderered-products.component.css']
})
export class OrdereredProductsComponent implements OnInit {

  constructor(private route: ActivatedRoute, private orderService: OrderService) { }
  @Input() tracker!: string
  @Input() orderItems!: OrderItem[]
  pageNum?: number

  ngOnInit(): void {
    this.pageNum = 1
    this.tracker = this.route.snapshot.params.tracker
    this.viewProducts()
  }
  viewProducts() {
    this.orderService.getOrderItemsByTracking(this.tracker).subscribe((response) => {
      console.log(response)
      this.orderItems = response;
    })
  }

}
