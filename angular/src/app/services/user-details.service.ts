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
  postUserDetails(data: any): void
  {
    const url = config.apiBaseURL + "/api/v2/users/" + this.userId;
    this.httpClient.post(url, data);
  }
  changePassword(data: any): void
  {
    const url = config.apiBaseURL + "/api/v2/users/" + this.userId;
    this.httpClient.post(url, data);
  }
}