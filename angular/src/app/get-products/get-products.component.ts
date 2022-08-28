import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { subscribeOn } from 'rxjs';

@Component({
  selector: 'app-get-products',
  templateUrl: './get-products.component.html',
  styleUrls: ['./get-products.component.css']
})
export class GetProductsComponent implements OnInit {
  
  public products:string  = "";

  constructor(private http : HttpClient) { }

  ngOnInit(): void {
    this.http.get("http://localhost:8080/product").subscribe(products => {
      this.products = products.toString();
    })

  }
}
