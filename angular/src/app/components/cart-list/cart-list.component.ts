import { Component, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { CartItem } from 'src/app/models/cart-item.model';
import { Product } from 'src/app/models/product.model';
import { CartService } from 'src/app/services/cart.service';

@Component({
  selector: 'app-cart-list',
  templateUrl: './cart-list.component.html',
  styleUrls: ['./cart-list.component.css']
})



export class CartListComponent implements OnInit {
  cartItems?: CartItem[];
  cartLength = 0;
  isProductAmountEditable = false

  constructor(private cartService: CartService) { }

  ngOnInit(): void {

    //must be first for live crud update to list
    this.cartService.activateWatcher().subscribe((response) => {
      this.cartItems = response;
    })

    this.cartService.viewItems();
    this.cartLength = this.cartItems?.length ?? 0;


  }

  removeItem(product: Product) {
    this.cartService.removeProduct(product);
  }

  onEditProductAmount(val: boolean) {
    this.isProductAmountEditable = val;
  }

  onSaveProductAmount(item: CartItem) {
    let amt = Number(item.amt)
    if (Number.isInteger(amt) && amt > 0) {
      this.cartService.updateCart(this.cartItems!);
    } else {
      item.amt = 1
      this.cartService.updateCart(this.cartItems!);
    }

    this.isProductAmountEditable = false;
  }

}
