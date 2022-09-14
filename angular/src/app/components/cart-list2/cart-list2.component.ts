import { Component, OnInit } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { Product } from 'src/app/models/product.model';
import { Cart2Service } from 'src/app/services/cart2.service';

@Component({
  selector: 'app-cart-list2',
  templateUrl: './cart-list2.component.html',
  styleUrls: ['./cart-list2.component.css']
})
export class CartList2Component implements OnInit {
  cartItems: any[];
  pageNum!: number;

  constructor(private cartService: Cart2Service) { }

  ngOnInit() {
    this.pageNum = 1;

    this.cartService.getUserCart()

    this.cartService.activateWatcher().subscribe(res => {
      this.cartItems = res
    })


  }

  updateAmount(item: any) {
    this.cartService.updateItemAmount(item)
  }

  deleteItem(item: any) {
    this.cartService.deleteItem(item)
  }

}
