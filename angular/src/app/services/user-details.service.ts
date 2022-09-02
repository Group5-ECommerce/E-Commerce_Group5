import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { OktaAuthStateService, OKTA_AUTH } from '@okta/okta-angular';
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

  constructor(private _oktaStateService: OktaAuthStateService, private httpClient: HttpClient) {
    this._oktaStateService.authState$.subscribe(
      (s) => {
        this.email = s.idToken!.claims.email!;
        let fullName = s.idToken?.claims.name!;
        let splitName = fullName.split(' ');
        this.firstName = splitName[0];
        this.lastName = splitName[1];
        this.username = s.idToken!.claims.preferred_username!;
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
      .subscribe((response) => { console.log(response); return true; });
  }
  changePassword(data: any) {
    const url = config.apiBaseURL + "/api/v1/authn/credentials/change_password";
    return this.httpClient.post(config.apiBaseURL + "/api/v1/authn", data).subscribe();
    
    return this.httpClient.post(url, data).pipe(catchError(async (error) => {console.log(error); return false;}))
      .subscribe((response) => { console.log(response); return true; });
  }

}
