import { Inject, Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import {OKTA_AUTH} from '@okta/okta-angular';
import { OktaAuth} from '@okta/okta-auth-js';




@Injectable()
export class AdminGuard implements CanActivate {    
    constructor(@Inject(OKTA_AUTH) private _oktaAuth: OktaAuth, private router: Router) { }

    async canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot){
         const auth = await this._oktaAuth.isAuthenticated();
         if (auth == true) {

            // We can probably cache the user info
            const user = await this._oktaAuth.getUser();
            const userDetails = JSON.stringify(user);
            const userJSON = JSON.parse(userDetails);
            if (userJSON.groups && userJSON.groups.includes("Admin")){
                return true;
            }
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
    constructor(@Inject(OKTA_AUTH) private _oktaAuth: OktaAuth, private router: Router) { }

    async canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot){
         const auth = await this._oktaAuth.isAuthenticated();
         if (auth == true) {

            const user = await this._oktaAuth.getUser();
            const userDetails = JSON.stringify(user);
            const userJSON = JSON.parse(userDetails);
            if (userJSON.groups && userJSON.groups.includes("Customer")){
                return true;
            }
        }
        this._oktaAuth.signInWithRedirect({'originalUri': state.url});
        return false;
    }

    async canActivateChild(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        return await this.canActivate(route, state);
    }
}