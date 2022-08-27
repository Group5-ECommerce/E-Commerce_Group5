import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { OktaAuthModule, OKTA_CONFIG } from '@okta/okta-angular';
import { OktaAuth } from '@okta/okta-auth-js';
import { ProfileComponent } from './profile/profile.component';

import { AddtocartComponent } from './addtocart/addtocart.component';

// This page may be helpful for getting these values: https://developer.okta.com/docs/guides/sign-into-spa-redirect/angular/main/#find-your-config-values
// This page is helpful for future work: https://developer.okta.com/docs/guides/sign-into-spa-redirect/angular/main/#sign-in-a-user
const oktaAuth = new OktaAuth({
  issuer: "https://dev-54891741.okta.com/oauth2/default",
  clientId: "0oa69rnslcIC5GF4E5d7",
  redirectUri: window.location.origin + '/login/callback'
});

@NgModule({
  declarations: [
    AppComponent,
    ProfileComponent,
    CartComponent,
    AddtocartComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    OktaAuthModule,
    FormsModule, 
    HttpClientModule
  ],
  providers: [{ provide: OKTA_CONFIG, useValue: { oktaAuth } }],
  bootstrap: [AppComponent]
})
export class AppModule { }
