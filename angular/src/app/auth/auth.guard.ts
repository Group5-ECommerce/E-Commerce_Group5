import { Inject, Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import {OKTA_AUTH, OktaAuthStateService} from '@okta/okta-angular';
import { OktaAuth} from '@okta/okta-auth-js';
import { firstValueFrom, Observable, Subject } from 'rxjs';




@Injectable()
export class AdminGuard implements CanActivate {    
    constructor(@Inject(OKTA_AUTH) private _oktaAuth: OktaAuth, private oktaSvc:OktaAuthStateService, private router: Router) { }

    async canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot){
        const auth = await this._oktaAuth.isAuthenticated();
        if (auth == true) {
           // Thanks to https://stackoverflow.com/a/71136301 for this await observable's first value structure.
           // This makes the function wait to return until the service's request comes back as true or false.
           const isAdmin = await firstValueFrom(this.oktaSvc.hasAnyGroups("Admin"));

           return isAdmin;
       }
       this._oktaAuth.signInWithRedirect({'originalUri': state.url});
       return false;
    }

    async canActivateChild(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        return await this.canActivate(route, state);
    }
}

@Injectable()
export class CustomerGuard implements CanActivate {    
    constructor(@Inject(OKTA_AUTH) private _oktaAuth: OktaAuth, private oktaSvc:OktaAuthStateService, private router: Router) { }

    async canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot){
         const auth = await this._oktaAuth.isAuthenticated();
         if (auth == true) {
            let isCustomer = await firstValueFrom(this.oktaSvc.hasAnyGroups("Customer"));

            return isCustomer;
        }
        this._oktaAuth.signInWithRedirect({'originalUri': state.url});
        return false;
    }

    async canActivateChild(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        return this.canActivate(route, state);
    }
}