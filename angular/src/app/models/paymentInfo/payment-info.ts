import { Address } from "../address/address";
import { OrderAddress } from "../order-address";

export class PaymentInfo 
{
    billingAddressId: OrderAddress;
    shippingAddressId: OrderAddress;
}
