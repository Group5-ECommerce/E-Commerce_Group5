import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, ElementRef, Inject, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { OktaAuthStateService, OKTA_AUTH } from '@okta/okta-angular';
import { AuthState, HttpRequestClient, OktaAuth } from '@okta/okta-auth-js';
import { filter, map, Observable } from 'rxjs';
import { Cart2Service } from './services/cart2.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {
  title = 'okta-angular-quickstart';
  isVisible: boolean = false;
  cartUrl = "http://localhost:8080/cart"

  @ViewChild('userBtn') userButton: ElementRef;

  public isAuthenticated$!: Observable<boolean>;


  constructor(private _router: Router, private _oktaStateService: OktaAuthStateService, @Inject(OKTA_AUTH) private _oktaAuth: OktaAuth, private http: HttpClient, private cartService: Cart2Service) { }

  public ngOnInit(): void {
    this.isAuthenticated$ = this._oktaStateService.authState$.pipe(
      filter((s: AuthState) => !!s),
      map((s: AuthState) => s.isAuthenticated ?? false)
    );
    window.onclick = (e) => {
      if (this.isVisible && e.target !== this.userButton.nativeElement) this.toggleDropdown();
    };
    //works but duplicates on refresh and also persists when sign out in db
    this._oktaStateService.authState$.pipe(
      filter((authState: AuthState) => !!authState && !!authState.isAuthenticated),
      map((authState) => authState.idToken?.claims.sub)
    ).subscribe(id => {
      this.http.get(this.cartUrl).subscribe(items => {
        this.cartService.fillCartWithProducts(items)
      })
      console.log(id);
    });


  }

  public async signIn(): Promise<void> {
    // This may be useful in the future: { originalUri: '/' }
    await this._oktaAuth.signInWithRedirect().then(_ => {

      this._router.navigate(['/product']);

    }
    );
  }

  public uploadCart() {
    this.http.get(this.cartUrl).subscribe(items => this.cartService.fillCartWithProducts(items))
  }

  public async signOut(): Promise<void> {
    //one case is if user never manually signs out, not sure if the cart will get saved
    let cartItems = await this.cartService.getUserCart();

    // const headers = new HttpHeaders({ 'Content-Type': 'application/json; charset=utf-8' });

    this.http.put(this.cartUrl, cartItems).subscribe(); //void

    //clear cart after
    await this.cartService.clearCart();

    await this._oktaAuth.signOut();
  }

  toggleDropdown() {
    this.isVisible = !this.isVisible;
    document.getElementById("accountDropdown")?.classList.toggle("show");
  }


}
