import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from "../models/product.model";

const baseUrl = "http://localhost:8080/cart";
@Injectable({
  providedIn: 'root'
})
export class AddtocartserviceService {

  constructor(private http: HttpClient) { }

  addProduct(data: Product) {
    const cartItem = { product: data, amt: 0 };
    const cart = localStorage.getItem('cart');
    if (cart) {
      const cartJSON = JSON.parse(cart);
      cartJSON.cart.push(cartItem);
      const cartString = JSON.stringify(cartJSON);
      localStorage.setItem("cart", cartString);
    }
    else {
      const startCart = {"cart": [cartItem]};
      localStorage.setItem('cart', JSON.stringify(startCart));
    }


  }
}
