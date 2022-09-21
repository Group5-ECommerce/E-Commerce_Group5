import { HttpClient } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { OKTA_AUTH } from '@okta/okta-angular';
import { OktaAuth } from '@okta/okta-auth-js';
import { catchError } from 'rxjs';
import { config } from 'src/config/app.config';

@Injectable({
  providedIn: 'root'
})
export class UserDetailsService {

  username: String;
  lastName: String;
  firstName: String;
  email: String;
  id: String;

  constructor(private httpClient: HttpClient, @Inject(OKTA_AUTH) private _oktaAuth: OktaAuth) {
    // this.updateUserDetails();
  }
  
  updateUserDetails(){
    this._oktaAuth.tokenManager.get("idToken").then(
      (id) => {
        this.email = id.claims.email!;
        let fullName = id.claims.name!;
        let splitName = fullName.split(' ');
        this.firstName = splitName[0];
        this.lastName = splitName[1];
        this.username = id.claims.preferred_username!;
        this.id = id.claims.sub;
      }
    );
  }

  postUserDetails(data: any) {
    const url = config.apiBaseURL + "/api/v1/users/me";
    return this.httpClient.post(url, data).pipe(catchError(async (e) => {
      if (e.error.errorCode == "E0000001") {
        alert("Error: Choose another email. That email corresponds to another account");
      }
      return false;
    }))
      .subscribe((response: any) => {
        this._oktaAuth.tokenManager.renew('idToken').then( token => this.updateUserDetails());
        return true;
      }
      );
  }
  changePassword(data: any) {
    const url = config.apiBaseURL + "/api/v1/authn/credentials/change_password";
    return this.httpClient.post(config.apiBaseURL + "/api/v1/authn", data).subscribe();

    return this.httpClient.post(url, data).pipe(catchError(async (error) => { console.log(error); return false; }))
      .subscribe((response) => { console.log(response); return true; });
  }

}
