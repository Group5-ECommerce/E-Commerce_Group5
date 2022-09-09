import { Address } from "../address/address";

export class PaymentInfo 
{
    cardHolderFirstName : string;
    cardHolderLastName: string;
    cardNumber: string;
    cvv:string;
    billingAddressId: Address;
    shippingAddressId: Address;
}
