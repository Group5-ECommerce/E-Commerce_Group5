import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OktaAuthGuard, OktaCallbackComponent } from '@okta/okta-angular';
import { AddProductComponent } from './components/admin/add-product/add-product.component';
import { EditProductComponent } from './components/admin/edit-product/edit-product.component';
import { AdminProductListComponent } from './components/admin/product-list/admin-product-list.component';
import { CustomerGuard, AdminGuard } from './auth/auth.guard';
import { CustomerProductListComponent } from './components/product-list/customer-product-list.component';
import { ProfileComponent } from './components/profile/profile.component';
import { EditUserComponent } from './components/edit-user/edit-user.component';
import { ChangePasswordComponent } from './change-password/change-password.component';
import { CartListComponent } from './components/cart-list/cart-list.component';
import { CheckoutComponent } from './components/checkout/checkout.component';


const routes: Routes = [{ path: 'login/callback', component: OktaCallbackComponent },
{ path: 'profile', component: ProfileComponent, canActivate: [CustomerGuard] },
{ path: 'product', component: CustomerProductListComponent},
  { path: 'product-list', component: AdminProductListComponent, canActivate: [AdminGuard]},
  { path: 'add-product', component: AddProductComponent, canActivate: [AdminGuard]},
  { path: 'edit-product/:id', component: EditProductComponent, canActivate: [AdminGuard]},
  {path: 'edit-user', component: EditUserComponent, canActivate: [OktaAuthGuard]},
  {path: 'change-password', component: ChangePasswordComponent, canActivate: [OktaAuthGuard]},
  { path: 'cart-list', component: CartListComponent },
  { path: 'checkout', component: CheckoutComponent, canActivate: [CustomerGuard]}];

@NgModule({
  imports: [RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload' })],
  exports: [RouterModule],
})
export class AppRoutingModule { }
