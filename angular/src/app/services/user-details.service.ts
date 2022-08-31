import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { OktaAuthStateService } from '@okta/okta-angular';
import { config } from 'src/config/app.config';

@Injectable({
  providedIn: 'root'
})
export class UserDetailsService {

  username:String;
  userId:String;
  constructor(private _oktaStateService: OktaAuthStateService, private httpClient: HttpClient) { 
    this._oktaStateService.authState$.subscribe(
      (s) => {
        this.username = s.accessToken!.claims.sub;
        this.userId = (s.accessToken!.claims as any).uid;
      }
    );
  }
  postUserDetails(data: any)
  {
    const url = config.apiBaseURL + "/api/v1/users/me";
    console.log(url);
    return this.httpClient.post(url, data).subscribe();
  }
  changePassword(data: any)
  {
    const url = config.apiBaseURL + "/api/v1/users/me/credentials/change_password";
    return this.httpClient.post(url, data).subscribe();
  }
}
