import { CartItem } from "../cart-item.model";
import { PaymentInfo } from "../paymentInfo/payment-info";

export class Purchase
{
    payment: PaymentInfo;
    items: CartItem[];
    message: string;
}
