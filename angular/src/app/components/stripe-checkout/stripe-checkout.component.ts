import { Token, TokenType } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { getToken } from '@okta/okta-auth-js';


@Component({
  selector: 'app-stripe-checkout',
  templateUrl: './stripe-checkout.component.html',
  styleUrls: ['./stripe-checkout.component.css']
})
export class StripeCheckoutComponent implements OnInit {

  strikeCheckout:any = null;
  token: any
  
  constructor() { }

  ngOnInit(): void {
    this.stripePaymentGateway();
  }
  
  checkout(amount: number) {
    const strikeCheckout = (<any>window).StripeCheckout.configure({
      key: 'pk_test_51LiLnEH4jfIC0NAIoZefd4iPzn8fBxQDTHtqwIng6mjZp0OExRBYbCZFU2V9XgHmD6BGpWga6Q0u6uPr9kWUBl9l000AZGoBE2',
      locale: 'auto',
      token: function (stripeToken: any) {
        console.log(stripeToken)
        alert('Stripe token generated!!!');
      }
    });

    strikeCheckout.open({
      name: 'Checkout',
      description: 'Payment Processing',
      amount: amount * 100
    });
  }
  
  stripePaymentGateway() {
    if(!window.document.getElementById('stripe-script')) {
      const script = window.document.createElement("script");
      script.id = "stripe-script";
      script.src = "https://checkout.stripe.com/checkout.js";
      script.type = "text/javascript";

      script.onload = () => {
        this.strikeCheckout = (<any>window).StripeCheckout.configure({
          key: 'pk_test_51LfnvqA8l25QfxG8icmTDebbGdc4rlLM5rQwBOAHgtETEJIz0tPq2vMDC5bdOHO8TDWwBcsFYIpO9NqpjjnynSmS00BmAwhDB3',
          locale: 'auto',
          token: function (token: any) {
            console.log(token)
            alert('Payment via stripe was successfull!!!');
          }
        });
      }
        
      window.document.body.appendChild(script);
    }
  }


}
