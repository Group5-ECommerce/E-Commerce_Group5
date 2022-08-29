import { Component, OnInit } from '@angular/core';
import { OktaAuthStateService } from '@okta/okta-angular';
import { filter, map, Observable } from 'rxjs';
import { AuthState } from '@okta/okta-auth-js';

@Component({
  selector: 'app-profile',
  template: `
  <div class="profile-card text-center">
    <div class="shield">
    <h1 *ngIf="name$ | async as name">
        Hello {{name}}!
    </h1>
    </div>
    <div *oktaHasAnyGroup="['Admin']">
      <h2>Welcome admin</h2>
    </div>
    <div *oktaHasAnyGroup="['Customer']">
      <h2>Welcome Customer</h2>
    </div>
  </div>
  `,
  styleUrls: ['./profile.component.css']
})

export class ProfileComponent implements OnInit {

  public name$!: Observable<string>;

  constructor(private _oktaAuthStateService: OktaAuthStateService) { }

  public ngOnInit(): void {
    this.name$ = this._oktaAuthStateService.authState$.pipe(
      filter((authState: AuthState) => !!authState && !!authState.isAuthenticated),
      map((authState: AuthState) => authState.idToken?.claims.name ?? '')
    );
  }
}

