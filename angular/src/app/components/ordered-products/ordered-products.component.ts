import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { OrderItem } from 'src/app/models/order-item.model';
import { Product } from 'src/app/models/product.model';
import { OrderService } from 'src/app/services/order.service';

@Component({
  selector: 'app-ordered-products',
  templateUrl: './ordered-products.component.html',
  styleUrls: ['./ordered-products.component.css']
})
export class OrderedProductsComponent implements OnInit {

  constructor(private route: ActivatedRoute, private orderService: OrderService) { }
  @Input() tracker!: string
  @Input() orderItems!: OrderItem[]
  @Input() hideBackButton: boolean = false;
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
