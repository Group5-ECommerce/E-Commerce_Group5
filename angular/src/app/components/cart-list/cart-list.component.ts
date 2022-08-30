import { Component, OnInit } from '@angular/core';
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
  constructor(private cartService: CartService) { }

  ngOnInit(): void {
    this.cartItems = this.cartService.viewItems();
    this.cartLength = this.cartItems?.length ?? 0;
  }

}
