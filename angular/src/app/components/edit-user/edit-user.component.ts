import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { UserDetailsService } from 'src/app/services/user-details.service';
import { ReactiveFormsModule } from '@angular/forms';

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
  eUserForm: FormGroup;

  user = {'fName':'', 'lName':'', 'email' : '', 'username': ''};

  constructor(private userDetailsService:UserDetailsService) { }
  public ngOnInit(): void {
    this.user.email = this.userDetailsService.email as string;
    this.user.fName= this.userDetailsService.firstName as string;
    this.user.lName = this.userDetailsService.lastName as string;
    this.user.username = this.userDetailsService.username as string;

    this.eUserForm = new FormGroup({
      'fName' : new FormControl(null, Validators.required),
      'lName' : new FormControl(null, Validators.required),
      'email' : new FormControl(null, [Validators.required, Validators.email]),
      'username' : new FormControl(null, [Validators.required, Validators.minLength(7)]),
    })
  }

  updateUserInfo(e: Event) {
    e.preventDefault();
    //let fName = (<HTMLInputElement>document.querySelector("#editUserForm #fName")).value;
    //let lName = (<HTMLInputElement>document.querySelector("#editUserForm #lName")).value;
    //let email = (<HTMLInputElement>document.querySelector("#editUserForm #email")).value;
    //let username = (<HTMLInputElement>document.querySelector("#editUserForm #username")).value;
    let fName = this.user.fName;
    let lName = this.user.lName;
    let email = this.user.email;
    let username = this.user.username;

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

