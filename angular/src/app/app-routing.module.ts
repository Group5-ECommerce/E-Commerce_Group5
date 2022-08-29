import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OktaCallbackComponent } from '@okta/okta-angular';
import { AddtocartComponent } from './addtocart/addtocart.component';
import { AddProductComponent } from './add-product/add-product.component';
import { EditProductComponent } from './edit-product/edit-product.component';
import { AdminProductListComponent } from './product-list/admin-product-list.component';
import { ProfileComponent } from './profile/profile.component';
import { CustomerGuard, AdminGuard} from './auth/auth.guard';
import { CustomerProductListComponent } from './components/product-list/customer-product-list.component';


const routes: Routes = [{ path: 'login/callback', component: OktaCallbackComponent },
{ path: 'profile', component: ProfileComponent, canActivate: [CustomerGuard] },
{ path: 'product', component: CustomerProductListComponent},
  { path: 'product-list', component: AdminProductListComponent},
  { path: 'add-product', component: AddProductComponent},
  { path: 'edit-product/:id', component: EditProductComponent},
  {path : 'addtocart', component: AddtocartComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }