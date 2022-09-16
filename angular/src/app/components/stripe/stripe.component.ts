import { Component, OnInit } from '@angular/core';
import stripe from 'stripe';

@Component({
  selector: 'app-stripe',
  templateUrl: './stripe.component.html',
  styleUrls: ['./stripe.component.css']
})
export class StripeComponent implements OnInit {
  elements: any;

  constructor() { }
  ngOnInit(): void {
    this.initialize();
    this.checkStatus();
    document.querySelector("#payment-form")!.addEventListener("submit", this.handleSubmit);
  }

  // The items the customer wants to buy
  items = [{ id: "xl-tshirt" }];

  // Fetches a payment intent and captures the client secret
  async initialize() {
    const response = await fetch("/create-payment-intent", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ items: this.items }),
    });
    const { clientSecret } = await response.json();

    const appearance = {
      theme: 'stripe',
    };
    this.elements = stripe.elements({ appearance, clientSecret });

    const paymentElement = this.elements.create("payment");
    paymentElement.mount("#payment-element");
  }

  async handleSubmit(e: any) {
    e.preventDefault();
    this.setLoading(true);

    const { error } = await stripe.confirmPayment({
      elements: this.elements,
      confirmParams: {
        // Make sure to change this to your payment completion page
        return_url: "http://localhost:4200/my-orders",
      },
    });

    // This point will only be reached if there is an immediate error when
    // confirming the payment. Otherwise, your customer will be redirected to
    // your `return_url`. For some payment methods like iDEAL, your customer will
    // be redirected to an intermediate site first to authorize the payment, then
    // redirected to the `return_url`.
    if (error.type === "card_error" || error.type === "validation_error") {
      this.showMessage(error.message);
    } else {
      this.showMessage("An unexpected error occurred.");
    }

    this.setLoading(false);
  }

  // Fetches the payment intent status after payment submission
  async checkStatus() {
    const clientSecret = new URLSearchParams(window.location.search).get(
      "payment_intent_client_secret"
    );

    if (!clientSecret) {
      return;
    }

    const { paymentIntent } = await stripe.retrievePaymentIntent(clientSecret);

    switch (paymentIntent.status) {
      case "succeeded":
        this.showMessage("Payment succeeded!");
        break;
      case "processing":
        this.showMessage("Your payment is processing.");
        break;
      case "requires_payment_method":
        this.showMessage("Your payment was not successful, please try again.");
        break;
      default:
        this.showMessage("Something went wrong.");
        break;
    }
  }

  // ------- UI helpers -------

  showMessage(messageText: string) {
    const messageContainer = document.querySelector("#payment-message")!;

    messageContainer.classList.remove("hidden");
    messageContainer.textContent = messageText;

    setTimeout(function () {
      messageContainer.classList.add("hidden");
      messageText = "";
    }, 4000);
  }

  // Show a spinner on payment submission
  setLoading(isLoading: boolean) {
    if (isLoading) {
      // Disable the button and show a spinner
      (document.querySelector("#submit")! as HTMLInputElement).disabled = true;
      document.querySelector("#spinner")!.classList.remove("hidden");
      document.querySelector("#button-text")!.classList.add("hidden");
    } else {
      (document.querySelector("#submit")! as HTMLInputElement).disabled = false;
      document.querySelector("#spinner")!.classList.add("hidden");
      document.querySelector("#button-text")!.classList.remove("hidden");
    }

  }
}
