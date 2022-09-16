import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Stripe } from 'stripe'

@Component({
  selector: 'app-stripe-checkout',
  templateUrl: './stripe-checkout.component.html',
  styleUrls: ['./stripe-checkout.component.css']
})
export class StripeCheckoutComponent implements OnInit {

  strikeCheckout: any = null;
  http: any;
  stripe: Stripe;

  constructor(http: HttpClient) { }

  ngOnInit(): void {
  }

  checkout(amount: number) {
    this.http.post("/create-payment-intent", async (req: any, res: any) => {
      const { items } = req.body;

      // Create a PaymentIntent with the order amount and currency
      const paymentIntent = await this.stripe.paymentIntents.create({
        amount: amount,
        currency: "usd",
        automatic_payment_methods: {
          enabled: true,
        },
      });
      
      res.send({
        clientSecret: "sk_test_51LigrpISwnjVGPNFsp5EzsNYDlhfDJELc6VcxF6dXB35J3re23qvgTo6cIaQn4I5Qrz7ME9jowvDUehDTnDiSwoV00bO0Bs69i",
      });
    }
  )}
}

