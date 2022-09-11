import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { OKTA_AUTH } from '@okta/okta-angular';
import { OktaAuth } from '@okta/okta-auth-js';
import { EMPTY } from 'rxjs';
import { Address } from 'src/app/models/address/address';
import { CartItem } from 'src/app/models/cart-item.model';
import { PaymentInfo } from 'src/app/models/paymentInfo/payment-info';
import { Purchase } from 'src/app/models/purchase/purchase';
import { CartService } from 'src/app/services/cart.service';
import { CheckoutService } from 'src/app/services/checkout.service';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {

  // checkoutFormGroup!: FormGroup;

  payment = new PaymentInfo()
  billingAddressId = new Address()
  shippingAddressId = new Address()
  cart: CartItem[]
  isSubmitted = false
  email: string
  name: string

  constructor(private service: CheckoutService, private cartService: CartService,  @Inject(OKTA_AUTH) private _oktaAuth: OktaAuth) { }
  async ngOnInit(): Promise<void>
   {
  //   this._oktaAuth.tokenManager.get("idToken").then(
  //     (s) => {
  //       this.email = s.claims.email!;
  //  })
    const idToken = await this._oktaAuth.tokenManager.get('idToken');
    this.email = idToken.claims.email!
    this.name = idToken.claims.name!
    
  }

  submitOrder() {
    this.payment.billingAddressId = this.billingAddressId
    this.payment.shippingAddressId = this.shippingAddressId

    const cart = localStorage.getItem('cart');
    this.cart = JSON.parse(cart!);

    if (!this.cart || this.cart.length === 0) {
      console.log("Error: you're cart is empty.")
      console.log(this.cart);
      return;
    }

    let purchase = new Purchase();
    purchase.payment = this.payment
    purchase.items = this.cart
    purchase.message = "Payment Succeeded!"

    console.log(purchase)
    console.log(this.email)
    console.log(this.name)


    // let email: string
    // email = "sds@sds"  // okta - emai

    this.service.confirmOrder(purchase, this.email, this.name).subscribe(
      {
        next: (res) => {
          console.log(res);
          this.isSubmitted = true;
          this.cartService.clearCart();
        }
      }
    )
  }
  closeAlert() {
    this.isSubmitted = false;
  }

  // constructor(private service: CheckoutService) { }

  // ngOnInit(): void
  // {
  //   this.checkoutFormGroup = this.formBuilder.group
  //   ({
  //     cardInformation: this.formBuilder.group(
  //       {
  //           cardHolderFirstName: new FormControl(''),
  //           cardHolderLastName: new FormControl(''),
  //           cardNumber: new FormControl('')
  //       }),

  //     shippingAddress: this.formBuilder.group(
  //       {
  //         streetAddress: new FormControl(''),
  //         city: new FormControl(''),
  //         state: new FormControl(''),
  //         zip: new FormControl(''),
  //         country: new FormControl('')

  //       }),

  //       billingAddress: this.formBuilder.group(
  //         {
  //           streetAddress: new FormControl(''),
  //           city: new FormControl(''),
  //           state: new FormControl(''),
  //           zip: new FormControl(),
  //           country: new FormControl('')

  //         })
  //    });
  // }

  // // console.log(this.checkoutFormGroup.get('cardInformation')?.value);
  // // console.log(this.checkoutFormGroup.get('shippingAddress')?.value);
  // // console.log(this.checkoutFormGroup.get('billingAddress')?.value);

  // onSubmit()
  // {
  //   // await fetch("http://localhost:8080/cart/1/2", {method: "post"});
  //   // await fetch("http://localhost:8080/cart").then(res => res.text().then(json => console.log(json)));

  //   let payment = new PaymentInfo ();
  //   payment.cardHolderFirstName = this.checkoutFormGroup.get(['cardInformation'])?.value.cardHolderFirstName
  //   payment.cardHolderLastName = this.checkoutFormGroup.get(['cardInformation'])?.value.cardHolderLastName
  //   payment.cardNumber = this.checkoutFormGroup.get(['cardInformation'])?.value.cardNumber
  //   payment.shippingAddressId = this.checkoutFormGroup.get(['shippingAddress'])?.value
  //   payment.billingAddressId = this.checkoutFormGroup.get(['billingAddress'])?.value


  //   let purchase = new Purchase();
  //   purchase.payment = payment;
  //   purchase.message = "Payment Succeeded!";

  //   console.log(JSON.stringify(purchase));

  //  this.service.confirmOrder(purchase).subscribe
  //  (
  //   {
  //      next: (res) =>
  //      {
  //       console.log(res);
  //      }
  //   }
  //  )

  // }

  copyShippingAddressToBillingAddress(event: { target: { checked: any; }; }) {

    if (event.target.checked) {
      this.shippingAddressId = this.billingAddressId;
    }
  }
}
