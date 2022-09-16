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
  // count: number;

  @ViewChild('userBtn') userButton: ElementRef;

  public isAuthenticated$!: Observable<boolean>;
  public isAdmin$: Observable<boolean>;

  name$!: Observable<String>;

  constructor(private _router: Router, private _oktaStateService: OktaAuthStateService, @Inject(OKTA_AUTH) private _oktaAuth: OktaAuth, private http: HttpClient, private cartService: Cart2Service) {
  }

  public ngOnInit(): void {

    this.isAuthenticated$ = this._oktaStateService.authState$.pipe(
      filter((s: AuthState) => !!s),
      map((s: any) => s.isAuthenticated ?? false)
    );

    this.name$ = this._oktaStateService.authState$.pipe(
      filter((authState: AuthState) => !!authState && !!authState.isAuthenticated),
      map((authState: AuthState) => authState.idToken?.claims.name?.split(' ')[0] ?? 'User')
    );

    this.isAdmin$ = this._oktaStateService.authState$.pipe(
      filter((authState: AuthState) => !!authState && !!authState.isAuthenticated),
      map((authState: any) => authState.idToken?.claims.groups.includes("Admin") ?? false)
    );

    // If the window is clicked and the target isn't the dropdown, if the dropdown is visible/open then close it.
    window.onclick = (e) => {
      if (this.isVisible && e.target !== this.userButton.nativeElement) this.toggleDropdown();
    };

    // this.isAuthenticated$.subscribe(isValid => {
    //   if (isValid) {
    //     this.count++;
    //     sessionStorage.setItem("count",this.count.toString());
    //     if ( sessionStorage.getItem("count")) {
    //       if(sessionStorage)
    //       this.http.get(this.cartUrl).subscribe(items =>
    //         this.cartService.fillCartWithProducts(items))
    //     }

    //   }


    // })

    // this._oktaStateService.authState$.pipe(
    //   filter((authState: AuthState) => !!authState && !!authState.isAuthenticated),
    //   map((authState) => {
    //     let sub = authState.idToken?.claims.sub
    //     console.log("sub: ", sub)
    //     if (sub !== null || sub !== undefined) {
    //       console.log("here")
    //       this.http.get(this.cartUrl).subscribe(items =>
    //         this.cartService.fillCartWithProducts(items))
    //     }
    //   })
    // )

    if (this._oktaAuth.isLoginRedirect()) {
      try {
        this._oktaAuth.handleLoginRedirect().then(res => {
          this._oktaAuth.tokenManager.get("idToken").then(_ => this.http.get(this.cartUrl).subscribe(items =>
            this.cartService.fillCartWithProducts(items)))
        });
      } catch (e) {
        console.log(e)
      }
    }
  }

  public async signIn() {
    // This may be useful in the future: { originalUri: '/' }
    //works but duplicates on refresh and also persists when sign out in db

    await this._oktaAuth.signInWithRedirect().then(_ => {

      // this._router.navigate(['/product']);

    });
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
    console.log("clearing cart")
    await this.cartService.clearCart();
    // debugger
    await this._oktaAuth.signOut();
  }

  toggleDropdown() {
    this.isVisible = !this.isVisible;
    document.getElementById("accountDropdown")?.classList.toggle("show");
  }

  getCartLength(): number {
    return this.cartService.length;
  }

}
