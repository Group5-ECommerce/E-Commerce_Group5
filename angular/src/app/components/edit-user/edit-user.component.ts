import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { OktaAuthStateService } from '@okta/okta-angular';
import { AuthState } from '@okta/okta-auth-js';
import { map, filter } from 'rxjs';
import { config } from 'src/config/app.config';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {

  username:String = "";
  userId:String = "";

  constructor(private _oktaStateService: OktaAuthStateService, private http: HttpClient) { }

  public ngOnInit(): void {
    this._oktaStateService.authState$.subscribe(
      (s) => {
        this.username = s.accessToken!.claims.sub;
        this.userId = (s.accessToken!.claims as any).uid;
      }
    );
  }

  updateUsername(e:Event){
    e.preventDefault();
    const url = config.issuer + "/api/v2/users/" + this.userId;
    // this.http.post(url, {"username", this.username});
  }
}

