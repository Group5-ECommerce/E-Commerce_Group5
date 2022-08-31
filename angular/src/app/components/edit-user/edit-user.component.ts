import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { OktaAuthStateService } from '@okta/okta-angular';
import { AuthState } from '@okta/okta-auth-js';
import { map, filter } from 'rxjs';
import { UserDetailsService } from 'src/app/services/user-details.service';
import { config } from 'src/config/app.config';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.css']
})
export class EditUserComponent implements OnInit {

  username: String = "";
  userId: String = "";

  constructor(private userDetailsService:UserDetailsService) { }

  public ngOnInit(): void {
  }

  updateUserInfo(e: Event) {
    e.preventDefault();
    let fName = (<HTMLInputElement>document.querySelector("#editUserForm #fName")).value;
    let lName = (<HTMLInputElement>document.querySelector("#editUserForm #lName")).value;
    let username = (<HTMLInputElement>document.querySelector("#editUserForm #username")).value;
    let email = (<HTMLInputElement>document.querySelector("#editUserForm #email")).value;

    let profile:Object = new Object();
    if (fName) Object.assign(profile, {'firstName': fName});
    if (lName) Object.assign(profile, {'lastName': lName});
    if (username) Object.assign(profile, {'username': username});
    if (email) Object.assign(profile, {'email': email});

    fetch("http://localhost:8080/product");

    this.userDetailsService.postUserDetails(profile);
  }
}

