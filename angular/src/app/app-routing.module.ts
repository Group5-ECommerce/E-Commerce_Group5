import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OktaCallbackComponent } from '@okta/okta-angular';
import { AddtocartComponent } from './addtocart/addtocart.component';
import { ProfileComponent } from './profile/profile.component';
import { CustomerGuard, AdminGuard} from './auth/auth.guard';
import { ProductListComponent } from './components/product-list/product-list.component';


const routes: Routes = [{ path: 'login/callback', component: OktaCallbackComponent },
{ path: 'profile', component: ProfileComponent, canActivate: [CustomerGuard] },
{ path: 'product', component: ProductListComponent},
{path : 'addtocart', component: AddtocartComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }