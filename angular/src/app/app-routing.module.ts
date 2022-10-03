import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OktaAuthGuard, OktaCallbackComponent } from '@okta/okta-angular';
import { AddProductComponent } from './components/admin/add-product/add-product.component';
import { EditProductComponent } from './components/admin/edit-product/edit-product.component';
import { AdminProductListComponent } from './components/admin/product-list/admin-product-list.component';
import { CustomerGuard, AdminGuard } from './auth/auth.guard';
import { CustomerProductListComponent } from './components/product-list/customer-product-list.component';
import { EditUserComponent } from './components/edit-user/edit-user.component';
import { CheckoutComponent } from './components/checkout/checkout.component';
import { OrderListComponent } from './components/admin/order-list/order-list.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { OrderTrackComponent } from './components/admin/order-track/order-track.component';
import { MyOrdersComponent } from './components/my-orders/my-orders.component';
import { IndexCartComponent } from './components/index-cart/index-cart.component';
import { OrderedProductsComponent } from './components/ordered-products/ordered-products.component';
import { AddressComponent } from './components/address/address.component';
import { UpdateAddressComponent } from './components/update-address/update-address.component';
import { DeleteAddressComponent } from './components/delete-address/delete-address.component';


const routes: Routes = [{ path: 'login/callback', component: OktaCallbackComponent },
{path: 'delete-address', component: DeleteAddressComponent, canActivate: [CustomerGuard]},
{path: 'update-address', component: UpdateAddressComponent, canActivate: [CustomerGuard]},
{path: 'address', component: AddressComponent, canActivate: [CustomerGuard]},
{ path: 'my-orders', component: MyOrdersComponent, canActivate: [CustomerGuard] },
{ path: 'my-orders/:tracker', component: OrderedProductsComponent, canActivate: [CustomerGuard] },
{ path: 'product', component: CustomerProductListComponent },
{ path: 'product-list', component: AdminProductListComponent, canActivate: [AdminGuard] },
{ path: 'add-product', component: AddProductComponent, canActivate: [AdminGuard] },
{ path: 'edit-product/:id', component: EditProductComponent, canActivate: [AdminGuard] },
{ path: 'cart-list', component: IndexCartComponent },
{ path: 'checkout', component: CheckoutComponent, canActivate: [CustomerGuard] },
{ path: 'order-list', component: OrderListComponent, canActivate: [AdminGuard] },
{ path: 'order-list/:tracker', component: OrderedProductsComponent, canActivate: [AdminGuard]  },
{path: 'edit-user', component: EditUserComponent, canActivate: [OktaAuthGuard]},
{path: 'change-password', component: ChangePasswordComponent, canActivate: [OktaAuthGuard]},
{path: 'order-track', component: OrderTrackComponent, canActivate: [AdminGuard] },
{ path: '', redirectTo: "product-list", pathMatch: "full"},
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload' })],
  exports: [RouterModule],
})
export class AppRoutingModule { }
