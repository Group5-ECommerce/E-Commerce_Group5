import { Component, OnInit } from '@angular/core';
import { AddtocartserviceService } from '../services/addtocartservice.service';
import { Product } from '../model/product';

@Component({
  selector: 'app-addtocart',
  templateUrl: './addtocart.component.html',
  styleUrls: ['./addtocart.component.css']
})
export class AddtocartComponent implements OnInit {

  constructor(private addtocartservice:AddtocartserviceService ) { }
  ngOnInit(): void {
  }

  product?: Product;
  saveToCart(): void
  {
    
    const data = {
      productId: this.product?.productId,
      productName: this.product?.productName,
      productStock: this.product?.productStock,
      productImage: this.product?.productImage,
      productPrice: this.product?.productPrice,
      storageId: this.product?.storageId
    };
    this.addtocartservice.addProduct(data).subscribe({
      next: (res) => {
        console.log(res);
      }
    })

  }
}
