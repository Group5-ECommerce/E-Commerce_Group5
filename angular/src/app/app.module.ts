import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptor } from './auth/auth.interceptor';

import { OktaAuthModule, OKTA_CONFIG } from '@okta/okta-angular';
import { OktaAuth } from '@okta/okta-auth-js';
import { ProfileComponent } from './profile/profile.component';

import { config } from "../config/app.config";
import { GetProductsComponent } from './get-products/get-products.component';

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
    GetProductsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    OktaAuthModule,
    HttpClientModule
  ],
  providers: [{ provide: OKTA_CONFIG, useValue: { oktaAuth } },
  { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
