import { Component, OnInit } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Product } from 'src/app/models/product.model';
import { IndexCartService } from 'src/app/services/index-cart.service';

@Component({
  selector: 'app-index-cart',
  templateUrl: './index-cart.component.html',
  styleUrls: ['./index-cart.component.css']
})
export class IndexCartComponent implements OnInit {
  cartItems: any[];
  pageNum!: number;
  totalPrice = 0;

  constructor(private cartService: IndexCartService) { }

  ngOnInit() {
    this.pageNum = 1;

    this.cartService.getUserCart()

    this.cartService.activateWatcher().subscribe(res => {
      this.cartItems = res
      this.totalPrice = this.cartItems.reduce((accumalator, item) => {
        accumalator = accumalator + item.productPrice * item.amt;
        return accumalator;
      }, 0);
    })


  }

  updateAmount(item: any) {
    this.cartService.updateItemAmount(item)
  }

  deleteItem(item: any) {
    this.cartService.deleteItem(item)
  }

  sortCart(field: string) {
    this.cartService.sortCart(field);
  }



}
