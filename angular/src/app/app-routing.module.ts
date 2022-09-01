import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OktaAuthGuard, OktaCallbackComponent } from '@okta/okta-angular';
import { AddProductComponent } from './components/admin/add-product/add-product.component';
import { EditProductComponent } from './components/admin/edit-product/edit-product.component';
import { AdminProductListComponent } from './components/admin/product-list/admin-product-list.component';
import { CustomerGuard, AdminGuard } from './auth/auth.guard';
import { CustomerProductListComponent } from './components/product-list/customer-product-list.component';
import { ProfileComponent } from './components/profile/profile.component';
import { CartListComponent } from './components/cart-list/cart-list.component';
import { CheckoutComponent } from './components/checkout/checkout.component';
import { OrderListComponent } from './components/order-list/order-list.component';
import { OrdereredProductsComponent } from './components/orderered-products/orderered-products.component';



const routes: Routes = [{ path: 'login/callback', component: OktaCallbackComponent },

{ path: 'profile', component: ProfileComponent, canActivate: [OktaAuthGuard] },
{ path: 'product', component: CustomerProductListComponent },
{ path: 'product-list', component: AdminProductListComponent, canActivate: [OktaAuthGuard] },
{ path: 'add-product', component: AddProductComponent, canActivate: [OktaAuthGuard] },
{ path: 'edit-product/:id', component: EditProductComponent, canActivate: [OktaAuthGuard] },
{ path: 'cart-list', component: CartListComponent },
{ path: 'checkout', component: CheckoutComponent, canActivate: [OktaAuthGuard] },
{ path: 'order-list', component: OrderListComponent },
{ path: 'ordered-products/:tracker', component: OrdereredProductsComponent },
{ path: '', redirectTo: "product", pathMatch: "full" },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload' })],
  exports: [RouterModule],
})
export class AppRoutingModule { }
