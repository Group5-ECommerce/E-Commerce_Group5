import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { AuthInterceptor } from './auth/auth.interceptor';

import { OktaAuthModule, OKTA_CONFIG } from '@okta/okta-angular';
import { OktaAuth } from '@okta/okta-auth-js';
import { ProfileComponent } from './profile/profile.component';
import {HttpClientModule} from '@angular/common/http'; 

import {config} from "../config/app.config";
import { ProductListComponent } from './product-list/product-list.component';
import { ProductService } from './product/product.service';
import { AddProductComponent } from './add-product/add-product.component';
import { FormsModule } from '@angular/forms';
import { EditProductComponent } from './edit-product/edit-product.component';
import { CustomerGuard, AdminGuard } from './auth/auth.guard';
import { config } from "../config/app.config";
import { GetProductsComponent } from './get-products/get-products.component';
import { ProductListComponent } from './components/product-list/product-list.component';

// This page may be helpful for getting these values: https://developer.okta.com/docs/guides/sign-into-spa-redirect/angular/main/#find-your-config-values
// This page is helpful for future work: https://developer.okta.com/docs/guides/sign-into-spa-redirect/angular/main/#sign-in-a-user
const oktaAuth = new OktaAuth({
  issuer: config.issuer,
  clientId: config.clientId,
  redirectUri: window.location.origin + '/login/callback',
});

@NgModule({
  declarations: [
    AppComponent,
    ProfileComponent,
    ProductListComponent,
    AddProductComponent,
    EditProductComponent
    GetProductsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    OktaAuthModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [{ provide: OKTA_CONFIG, useValue: { oktaAuth } }, ProductService],


  ],
  providers: [{ provide: OKTA_CONFIG, useValue: { oktaAuth } },
  { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
  CustomerGuard, AdminGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
