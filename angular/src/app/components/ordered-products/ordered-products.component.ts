import { Component, ElementRef, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { OrderItem } from 'src/app/models/order-item.model';
import { Product } from 'src/app/models/product.model';
import { OrderService } from 'src/app/services/order.service';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-ordered-products',
  templateUrl: './ordered-products.component.html',
  styleUrls: ['./ordered-products.component.css']
})
export class OrderedProductsComponent implements OnInit {

  constructor(private route: ActivatedRoute, private orderService: OrderService, private productService:ProductService) { }
  @Input() tracker!: string
  @Input() orderItems!: OrderItem[]
  @Input() hideBackButton: boolean = false;
  pageNum?: number
  hasEnteredRatingForProduct = new Array<boolean>();

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
  submitRating(id:number, rating:number, submitBtn:HTMLButtonElement){
    console.log(rating);
    this.productService.rateProduct(id, rating).subscribe({
      next: res =>{
        submitBtn.disabled = true;
        submitBtn.textContent = "Submitted";
      },
      error: e =>{

      }
    });
    console.log("submitting rating");
  }

}
