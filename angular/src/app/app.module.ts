import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { BrowserAnimationsModule } 
    from "@angular/platform-browser/animations";

import { AuthInterceptor } from './auth/auth.interceptor';

import { OktaAuthModule, OKTA_CONFIG } from '@okta/okta-angular';
import { OktaAuth } from '@okta/okta-auth-js';
import { ProfileComponent } from './components/profile/profile.component';
import { config } from "../config/app.config";
import { AdminProductListComponent } from './components/admin/product-list/admin-product-list.component';
import { ProductService } from './services/product.service';
import { AddProductComponent } from './components/admin/add-product/add-product.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { EditProductComponent } from './components/admin/edit-product/edit-product.component';
import { CustomerGuard, AdminGuard } from './auth/auth.guard';
import { CustomerProductListComponent } from './components/product-list/customer-product-list.component';
import { EditUserComponent } from './components/edit-user/edit-user.component';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
import { CheckoutComponent } from './components/checkout/checkout.component';
import { OrderListComponent } from './components/order-list/order-list.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { FilterPipe } from './pipes/filter.pipe';
import { OrderTrackComponent } from './components/order-track/order-track.component';
import { MyOrdersComponent } from './components/my-orders/my-orders.component';

import { RatingModule } from "primeng/rating";
import { IndexCartComponent } from './components/index-cart/index-cart.component';
import { IndexedDatabase } from './indexeddb';
import { OrderedProductsComponent } from './components/ordered-products/ordered-products.component';


// This page may be helpful for getting these values: https://developer.okta.com/docs/guides/sign-into-spa-redirect/angular/main/#find-your-config-values
// This page is helpful for future work: https://developer.okta.com/docs/guides/sign-into-spa-redirect/angular/main/#sign-in-a-user
const oktaAuth = new OktaAuth({
  issuer: config.issuer,
  clientId: config.clientId,
  redirectUri: window.location.origin + '/login/callback',
  scopes: ["openid", "profile", "email", "groups", "okta.users.manage.self"],
  tokenManager: {
    storage: 'sessionStorage',
    autoRenew: true
  }
});

@NgModule({
  declarations: [
    AppComponent,
    ProfileComponent,
    AdminProductListComponent,
    CustomerProductListComponent,
    AddProductComponent,
    EditProductComponent,
    EditUserComponent,
    ChangePasswordComponent,
    CheckoutComponent,
    OrderListComponent,
    OrderedProductsComponent,
    OrderTrackComponent,
    FilterPipe,
    MyOrdersComponent,

    IndexCartComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    OktaAuthModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    NgxPaginationModule,
    BrowserAnimationsModule,
    RatingModule
  ],
  providers: [{ provide: OKTA_CONFIG, useValue: { oktaAuth } },
  { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    CustomerGuard, AdminGuard, IndexedDatabase
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
