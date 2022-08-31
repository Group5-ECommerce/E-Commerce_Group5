import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { OktaAuthStateService } from '@okta/okta-angular';
import { catchError } from 'rxjs';
import { config } from 'src/config/app.config';

@Injectable({
  providedIn: 'root'
})
export class UserDetailsService {

  username: String;
  userId: String;
  constructor(private _oktaStateService: OktaAuthStateService, private httpClient: HttpClient) {
    this._oktaStateService.authState$.subscribe(
      (s) => {
        this.username = s.accessToken!.claims.sub;
        this.userId = (s.accessToken!.claims as any).uid;
      }
    );
  }
  postUserDetails(data: any) {
    const url = config.apiBaseURL + "/api/v1/users/me";
    return this.httpClient.post(url, data).pipe(catchError(async (error) => console.log("error")))
      .subscribe((response) => { console.log("response") }
      );
  }
  changePassword(data: any) {
    const url = config.apiBaseURL + "/api/v1/users/me/credentials/change_password";
    return this.httpClient.post(url, data).pipe(catchError(async (error) => console.log("error")))
      .subscribe();
  }
}
