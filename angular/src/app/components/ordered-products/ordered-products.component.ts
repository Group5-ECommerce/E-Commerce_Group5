import { Component, ElementRef, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Rating } from 'primeng/rating';
import { OrderItem } from 'src/app/models/order-item.model';
import { Product } from 'src/app/models/product.model';
import { OrderService } from 'src/app/services/order.service';
import { ProductService } from 'src/app/services/product.service';
import { UserDetailsService } from 'src/app/services/user-details.service';

@Component({
  selector: 'app-ordered-products',
  templateUrl: './ordered-products.component.html',
  styleUrls: ['./ordered-products.component.css']
})
export class OrderedProductsComponent implements OnInit {

  constructor(private route: ActivatedRoute, private orderService: OrderService, private productService: ProductService, private userService: UserDetailsService) { }
  @Input() tracker!: string
  @Input() orderItems!: OrderItem[]
  @Input() hideBackButton: boolean = false;
  pageNum?: number
  hasEnteredRatingForProduct = new Array<boolean>();
  isAdmin: boolean;
  // totalPrice!: number;

  ngOnInit(): void {
    this.pageNum = 1
    this.tracker = this.route.snapshot.params.tracker
    // HideBackButton is only set true when displaying on the order Tracking page.
    // (Order tracking sends the request itself, and passes in an array of orderItems to display. This prevents an unnecessary request.)

    if (!this.hideBackButton) {
      this.viewProducts();
    }

    this.isAdmin = this.userService.isAdmin;
  }

  // ngOnChanges(): void {
  //   if (this.orderItems && this.orderItems.length > 0) {
  //     this.totalPrice = this.orderItems.reduce((accumalator, item) => {
  //       return accumalator + item!.product!.productPrice * item.amt!;
  //     }, 0);
  //   }
  // }
  viewProducts() {
    this.orderService.getOrderItemsByTracking(this.tracker).subscribe((response) => {
      console.log(response)
      this.orderItems = response;
      // if (this.orderItems.length > 0) {
      //   this.totalPrice = this.orderItems.reduce((accumalator, item) => {
      //     return accumalator + item!.product!.productPrice * item.amt!;
      //   }, 0);
      // }

    })
  }
  submitRating(id: number, rating: Rating, submitBtn: HTMLButtonElement) {
    console.log(rating);
    this.productService.rateProduct(id, rating.value).subscribe({
      next: res => {
        submitBtn.disabled = true;
        submitBtn.textContent = "Submitted";
        rating.readonly = true;
      },
      error: e => {

      }
    });
    console.log("submitting rating");
  }

}
