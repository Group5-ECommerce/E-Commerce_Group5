import { Address } from "./address/address"
import { CartItem } from "./cart-item.model"
import { User } from "./user.model"

export class Order {
  items: CartItem[]
  orderId?: number
  user?: User
  trackingNumber?: string
  totalPrice?: number
  shippingAddress?: Address
  orderTime?: string
  orderStatus?: string
}
