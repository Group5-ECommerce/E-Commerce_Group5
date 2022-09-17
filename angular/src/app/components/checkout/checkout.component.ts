import { STRING_TYPE } from '@angular/compiler';
import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Event } from '@angular/router';
import { OKTA_AUTH } from '@okta/okta-angular';
import { OktaAuth } from '@okta/okta-auth-js';
import { EMPTY } from 'rxjs';
import { Address } from 'src/app/models/address/address';
import { CartItem } from 'src/app/models/cart-item.model';
import { Payment } from 'src/app/models/payment';
import { PaymentInfo } from 'src/app/models/paymentInfo/payment-info';
import { Purchase } from 'src/app/models/purchase/purchase';
import { CartService } from 'src/app/services/cart.service';
import { CheckoutService } from 'src/app/services/checkout.service';
import { environment } from 'src/environments/environment';
import { StripeCheckoutComponent } from '../stripe-checkout/stripe-checkout.component';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {

  // checkoutFormGroup!: FormGroup;

  payment = new PaymentInfo()
  stripe = Stripe(environment.stripePublishableKey)
  billingAddressId = new Address()
  shippingAddressId = new Address()
  cart: CartItem[]
  cardElement: any
  displayError: any = ""
  isSubmitted = false
  isConfirmed = false
  @ViewChild(StripeCheckoutComponent) strikeCheckout: StripeCheckoutComponent;
  email: string
  name: string


  constructor(private service: CheckoutService, private cartService: CartService, @Inject(OKTA_AUTH) private _oktaAuth: OktaAuth) { }
  async ngOnInit(): Promise<void>
   {
  //   this._oktaAuth.tokenManager.get("idToken").then(
  //     (s) => {
  //       this.email = s.claims.email!;
  //  })
    const idToken = await this._oktaAuth.tokenManager.get('idToken');
    this.email = idToken.claims.email!
    this.name = idToken.claims.name!
    
    this.setUpPaymentForm()

  }

  async submitOrder() {
    this.payment.billingAddressId = this.billingAddressId
    this.payment.shippingAddressId = this.shippingAddressId

    const cart = localStorage.getItem('cart');
    this.cart = JSON.parse(cart!);

    if (!this.cart || this.cart.length === 0) {
      console.log("Error: you're cart is empty.")
      console.log(this.cart);
      return;
    }

    let totalPrice: number
    totalPrice = 0;
    for(const c of this.cart)
    {
        totalPrice = totalPrice + (c.product.productPrice * c.amt);
    }

    console.log(totalPrice);

    let pmt = new Payment()
    pmt.amount = Math.round(totalPrice * 100)
    pmt.currency = "USD"


    let purchase = new Purchase();
    purchase.payment = this.payment
    purchase.items = this.cart
    purchase.message = "Payment Succeeded!"

    console.log(purchase)
    console.log(this.email)
    console.log(this.name)


    // let email: string
    // email = "sds@sds"  // okta - email
    
    this.service.createPaymentIntent(pmt).subscribe(
      (paymentIntentResponse) => {
        this.stripe.confirmCardPayment(paymentIntentResponse.client_secret,
          {
            payment_method: {
              card: this.cardElement,
              billing_details: {
                email: this.email,
                name: `${purchase.payment.billingAddressId.firstName} ${purchase.payment.billingAddressId.lastName}`,
                address: {
                  line1: purchase.payment.billingAddressId.streetAddress,
                  city: purchase.payment.billingAddressId.city,
                  state:  purchase.payment.billingAddressId.state,
                  postal_code:purchase.payment.billingAddressId.zip ,
                  country: purchase.payment.billingAddressId.country
                }
            }
          }
        })
        })

    this.service.confirmOrder(purchase, this.email, this.name).subscribe(
      {
        next: (res) => {
          console.log(res);
          this.isSubmitted = true;
          this.cartService.clearCart();
        }
      }
    )

   

   // this.strikeCheckout.checkout(totalPrice);
   // this.isConfirmed = true
  // this.isSubmitted = true;

  }

  setUpPaymentForm() 
  {
    var elements = this.stripe.elements();

    // Create a card element ... and hide the zip-code field
    this.cardElement = elements.create('card', { hidePostalCode: true });

    // Add an instance of card UI component into the 'card-element' div
    this.cardElement.mount('#card-element');

    // Add event binding for the 'change' event on the card element
    this.cardElement.on('change', (event) => {

      // get a handle to card-errors element
      this.displayError = document.getElementById('card-errors');

      if (event.complete) {
        this.displayError.textContent = "";
        this.isConfirmed = true;
      } else if (event.error) {
        // show validation error to customer
        this.displayError.textContent = event.error.message;
        this.isConfirmed = false;
      }
        else if (!event.complete)
        {
          this.isConfirmed = false;
        }
      

    });
    
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
