import { Inject, Injectable } from '@angular/core';
import {HttpRequest, HttpHandler, HttpEvent, HttpInterceptor} from '@angular/common/http';
import { Observable } from 'rxjs';
import { OKTA_AUTH } from '@okta/okta-angular';
import { OktaAuth } from '@okta/okta-auth-js';


// Thanks to the Okta guide for this code: https://developer.okta.com/docs/guides/sign-into-spa-redirect/angular/main/#use-the-access-token
@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(@Inject(OKTA_AUTH) private _oktaAuth: OktaAuth) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    return next.handle(this.addAuthHeaderToAllowedOrigins(request));
  }

  private addAuthHeaderToAllowedOrigins(request: HttpRequest<unknown>): HttpRequest<unknown> {
    let req = request;
    // Send the auth bearer token to all origins containing this string.
    const allowedOrigins = ['http://localhost:8080'];
    if (!!allowedOrigins.find(origin => request.url.includes(origin))) {
      const authToken = this._oktaAuth.getAccessToken();
      if (authToken != null) {
      req = request.clone({ setHeaders: { 'Authorization': `Bearer ${authToken}` } });
      }
    }

    return req;
  }
}
