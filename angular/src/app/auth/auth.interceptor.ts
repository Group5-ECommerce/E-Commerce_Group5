import { Inject, Injectable } from '@angular/core';
import {HttpRequest, HttpHandler, HttpEvent, HttpInterceptor} from '@angular/common/http';
import { Observable } from 'rxjs';
import { OKTA_AUTH } from '@okta/okta-angular';
import { OktaAuth } from '@okta/okta-auth-js';
import { config } from 'src/config/app.config';


// Thanks to the Okta guide for this code: https://developer.okta.com/docs/guides/sign-into-spa-redirect/angular/main/#use-the-access-token
@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(@Inject(OKTA_AUTH) private _oktaAuth: OktaAuth) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    return next.handle(this.addAuthHeaderToAllowedOrigins(request));
  }

  private addAuthHeaderToAllowedOrigins(request: HttpRequest<unknown>): HttpRequest<unknown> {
    let req = request;
    // The backend wants the ID token because it contains the user's groups.
    const backendOrigin = ['https://ecommerce-capstone-backend.azurewebsites.net'];
    if (!!backendOrigin.find(origin => request.url.includes(origin))) {
      const authToken = this._oktaAuth.getIdToken();
      if (authToken != null) {
        req = request.clone({ setHeaders: { 'Authorization': `Bearer ${authToken}` } });
      }
    }

    // The API wants the access token because it contains the user's scopes (mainly the manage.user.self API scope).
    const apiOrigin = [config.apiBaseURL];
    if (!!apiOrigin.find(origin => request.url.includes(origin))) {
      const authToken = this._oktaAuth.getAccessToken();
      if (authToken != null) {
        req = request.clone({ setHeaders: { 'Authorization': `Bearer ${authToken}` } });
      }
    }

    return req;
  }
}
