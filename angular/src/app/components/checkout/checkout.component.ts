import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { OKTA_AUTH } from '@okta/okta-angular';
import { OktaAuth } from '@okta/okta-auth-js';
import { Address } from 'src/app/models/address/address';
import { CartItem } from 'src/app/models/cart-item.model';
import { Payment } from 'src/app/models/payment';
import { PaymentInfo } from 'src/app/models/paymentInfo/payment-info';
import { Purchase } from 'src/app/models/purchase/purchase';
import { IndexCartService } from 'src/app/services/index-cart.service';
import { CheckoutService } from 'src/app/services/checkout.service';
import { environment } from 'src/environments/environment';
import { AddressService } from 'src/app/services/address.service';
import { OrderAddress } from 'src/app/models/order-address';

@Component({
  selector: 'app-checkout',
  templateUrl: './checkout.component.html',
  styleUrls: ['./checkout.component.css']
})
export class CheckoutComponent implements OnInit {

  // checkoutFormGroup!: FormGroup;

  payment = new PaymentInfo()
  stripe = Stripe(environment.stripePublishableKey)
  billingAddressId = new OrderAddress()
  shippingAddressId = new OrderAddress()
  currentUserAddress = new Address()
  val = new Address()
  cart: CartItem[]
  userAddress: Address[]
  cardElement: any
  displayError: any = ""
  isSubmitted = false
  isConfirmed = false
  autoCompleted = false
  email: string
  name: string
  id: string

  constructor(private service: CheckoutService,private addressService: AddressService, private cartService: IndexCartService, @Inject(OKTA_AUTH) private _oktaAuth: OktaAuth) { }
  async ngOnInit(): Promise<void> {
    //   this._oktaAuth.tokenManager.get("idToken").then(
    //     (s) => {
    //       this.email = s.claims.email!;
    //  })
    const idToken = await this._oktaAuth.tokenManager.get('idToken');
    this.email = idToken.claims.email!
    this.name = idToken.claims.name!
    

    this.setUpPaymentForm()

    this.getUserAddress()

  }

  async submitOrder() {
    this.payment.billingAddressId = this.billingAddressId
    this.payment.shippingAddressId = this.shippingAddressId
    let cart = await this.cartService.getUserCart();

    let backendCart: Array<any> = [];
    // Our backend uses a different format for cart items- an amt and a product.
    // So we map our frontend indexdb cart to this different format.
    cart.forEach((c: any) => {
      backendCart.push({
        "amt": c.amt,
        "product": {
          "productId": c.productId,
          "productName": c.productName,
          "productStock": c.productStock,
          "productImage": c.productImage,
          "productPrice": c.productPrice,
          "storageId": c.storageId,
          "category": c.category,
        }
      });
    })

    if (backendCart.length == 0) { //will always be array
      console.log("error loading cart")
      return;
    }

    console.log(backendCart);
    let totalPrice: number
    totalPrice = 0;
    for (const c of backendCart) {
      totalPrice = totalPrice + (c.product.productPrice * c.amt);
    }

    let pmt = new Payment()
    pmt.amount = Math.round(totalPrice * 100)
    pmt.currency = "USD"


    let purchase = new Purchase();
    purchase.payment = this.payment
    purchase.items = backendCart;
    purchase.message = "Payment Succeeded!"

    console.log(purchase)
    console.log(this.email)
    console.log(this.name)

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
                  state: purchase.payment.billingAddressId.state,
                  postal_code: purchase.payment.billingAddressId.zip,
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
  }

  setUpPaymentForm() {
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
      else if (!event.complete) {
        this.isConfirmed = false;
      }


    });

  }
  closeAlert() {
    this.isSubmitted = false;
  }

  copyShippingAddressToBillingAddress(event: { target: { checked: any; }; }) {

    if (event.target.checked) {
      this.shippingAddressId = this.billingAddressId;
    }
  }

  getUserAddress()
  {
    this.addressService.getAddressById().subscribe((response) =>
    {
      this.userAddress = response;
      console.log(this.userAddress);
    })
  }

  onSelectAddress()
  {
      //this.billingAddressId = a.value;
      let i = (document.getElementById('addressSelect') as HTMLInputElement).value;
      this.billingAddressId = this.userAddress[i] as OrderAddress;
      console.log(this.billingAddressId);
  }
}
