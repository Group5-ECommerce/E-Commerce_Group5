import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Product } from '../product/product';
import { ProductService } from '../product/product.service';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.css']
})
export class EditProductComponent implements OnInit {

  constructor(private route: ActivatedRoute, private productService: ProductService) { }
  id!: number;
  product = new Product();
  showAlert = false;
  
  ngOnInit(){
    this.id = this.route.snapshot.params.id;

    this.productService.getProduct(this.id).subscribe((response: any) => {
      this.product = response;
    })
  }

  updateProduct(){
    this.productService.updateProduct(this.product).subscribe((response) =>{
      console.log(response);
      this.product = new Product();
      this.showAlert = true;
    })
  }

  closeAlert(){
    this.showAlert = false;
  }
}
