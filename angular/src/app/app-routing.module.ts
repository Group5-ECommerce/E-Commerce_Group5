import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OktaCallbackComponent } from '@okta/okta-angular';
import { AddtocartComponent } from './addtocart/addtocart.component';

const routes: Routes = [{ path: 'login/callback', component: OktaCallbackComponent },
                        {path : 'addtocart', component: AddtocartComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
