import { Address } from "./address/address"
import { CartItem } from "./cart-item.model"
import { OrderAddress } from "./order-address"
import { User } from "./user.model"

export class Order {
  items: CartItem[]
  orderId?: number
  user?: User
  oktaId: string
  trackingNumber?: string
  totalPrice?: number
  shippingAddress?: OrderAddress
  orderTime?: string
  orderStatus?: string
}
