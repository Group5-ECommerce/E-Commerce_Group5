import { Component, OnInit } from '@angular/core';
import { AddtocartserviceService } from '../services/addtocartservice.service';
import { Product } from '../models/product.model';

@Component({
  selector: 'app-addtocart',
  templateUrl: './addtocart.component.html',
  styleUrls: ['./addtocart.component.css']
})
export class AddtocartComponent implements OnInit {

  constructor(private addtocartservice:AddtocartserviceService ) { }
  ngOnInit(): void {
  }

  product!: Product;
  saveToCart(product: Product): void
  {
    this.addtocartservice.addProduct(product);
  }
}
