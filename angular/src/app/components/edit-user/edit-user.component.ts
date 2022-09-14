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
  isSubmitted:boolean = false;
  isError:boolean = false;

  user = {'fName':'', 'lName':'', 'email' : '', 'username': ''};

  constructor(private userDetailsService:UserDetailsService) { }
 //Test commit for Azure 
  public ngOnInit(): void {
    this.user.email = this.userDetailsService.email as string;
    this.user.fName=this.userDetailsService.firstName as string;
    this.user.lName = this.userDetailsService.lastName as string;
    this.user.username = this.userDetailsService.username as string;
  }

  updateUserInfo(e: Event) {
    e.preventDefault();
    let fName = (<HTMLInputElement>document.querySelector("#editUserForm #fName")).value;
    let lName = (<HTMLInputElement>document.querySelector("#editUserForm #lName")).value;
    let email = (<HTMLInputElement>document.querySelector("#editUserForm #email")).value;
    let username = (<HTMLInputElement>document.querySelector("#editUserForm #username")).value;


    let data = {"profile": {}};
    if (fName) Object.assign(data.profile, {'firstName': fName});
    if (lName) Object.assign(data.profile, {'lastName': lName});
    if (email) Object.assign(data.profile, {'email': email});
    if (username) Object.assign(data.profile, {'login': username});

    // If any of these inputs are not null.
    if (fName || lName || email || username){
      this.userDetailsService.postUserDetails(data);
      this.isSubmitted = true;
    }
    else this.isError = true;
  }

  closeSubmitted(){
    this.isSubmitted = false;
  }

  closeError(){
    this.isError = false;
  }
}

