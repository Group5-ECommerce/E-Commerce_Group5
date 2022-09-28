import { Component, Injectable, OnInit } from '@angular/core';
import { OktaAuth } from '@okta/okta-auth-js';
import { UserDetailsService } from '../../services/user-details.service';

@Component({
  selector: 'app-change-panssword',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
@Injectable()
export class ChangePasswordComponent implements OnInit {
  ngOnInit(): void {
  }

  constructor(private userDetailsService:UserDetailsService) { }

  changePassword(e: Event) {
    e.preventDefault();
    let oldPass = (<HTMLInputElement>document.querySelector("#changePasswordForm #oldPass")).value;
    let newPass = (<HTMLInputElement>document.querySelector("#changePasswordForm #newPass")).value;
    let newPass2 = (<HTMLInputElement>document.querySelector("#changePasswordForm #newPass2")).value;

    if (!newPass || !newPass2 || !oldPass){
      alert("Please insert values for all textboxes.");
      return;
    }
    if ( newPass !== newPass2){
      alert("The two password do not match!");
      return;
    }

    let data:Object = new Object();
    Object.assign(data, {'oldPassword': oldPass, 'newPassword': newPass});

    this.userDetailsService.changePassword(data);
  }

}
