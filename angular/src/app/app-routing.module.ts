import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OktaCallbackComponent } from '@okta/okta-angular';
import { AddProductComponent } from './add-product/add-product.component';
import { EditProductComponent } from './edit-product/edit-product.component';
import { ProductListComponent } from './product-list/product-list.component';
import { ProfileComponent } from './profile/profile.component';
import { CustomerGuard, AdminGuard} from './auth/auth.guard';
import { ProductListComponent } from './components/product-list/product-list.component';


const routes: Routes = [{ path: 'login/callback', component: OktaCallbackComponent },
{ path: 'profile', component: ProfileComponent, canActivate: [CustomerGuard] },
{ path: 'product', component: ProductListComponent},
  { path: 'product-list', component: ProductListComponent},
  { path: 'add-product', component: AddProductComponent},
  { path: 'edit-product/:id', component: EditProductComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload' })],
  exports: [RouterModule],
})
export class AppRoutingModule { }
