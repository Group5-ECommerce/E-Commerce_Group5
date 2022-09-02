import { Address } from "../common/address/address"
import { CartItem } from "./cart-item.model"

export class Order {
  items: CartItem[]
  orderId?: number
  userId?: any
  trackingNumber?: string
  totalPrice?: number
  shippingAddress?: Address
  orderTime?: string
  orderStatus?: string
}
