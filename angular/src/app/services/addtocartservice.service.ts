import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../models/product.model';

const baseUrl = "http://localhost:8000/cart";
@Injectable({
  providedIn: 'root'
})
export class AddtocartserviceService {

  constructor(private http: HttpClient) { }

  addProduct(data: Product) {
    const cartItem = { product: data, amt: 1 };
    const cart = localStorage.getItem('cart');
    if (cart) {
      const cartJSON = JSON.parse(cart);
      cartJSON.push(cartItem);
      const cartString = JSON.stringify(cartJSON);
      localStorage.setItem("cart", cartString);
    }
    else {
      const startCart = [cartItem];
      localStorage.setItem('cart', JSON.stringify(startCart));
    }
  }

  removeProduct(data: Product) {
    const cart = localStorage.getItem('cart');
    let cartJSON = JSON.parse(cart!);
    cartJSON = cartJSON.filter((item: { product: Product, amt: 1 }) => item.product.productId !== data.productId);
    const cartString = JSON.stringify(cartJSON);
    localStorage.setItem("cart", cartString);
  }
}
