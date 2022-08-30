import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {
  ngOnInit(): void {
  }

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

    let profile:Object = new Object();
    if (oldPass) Object.assign(profile, {'oldPassword': oldPass});
    if (newPass) Object.assign(profile, {'newPassword': newPass});

    // const url = config.issuer + "/api/v2/users/" + this.userId;
    // console.log(url);
    //this.http.post(url, profile);
  }

}
