import { Component, OnInit } from '@angular/core';
import { Address } from 'src/app/models/address/address';
import { AddressService } from 'src/app/services/address.service';

@Component({
  selector: 'app-address',
  templateUrl: './address.component.html',
  styleUrls: ['./address.component.css']
})
export class AddressComponent implements OnInit {

  constructor(private addressService: AddressService) { }

  newAddress = new Address()
  isSubmitted = false;
  ngOnInit(): void {
  }

  submitNewAddress()
  {
    this.addressService.addAddress(this.newAddress).subscribe(
      {
        next: (res) => 
        {
          console.log(res);
          this.isSubmitted = true;
        }
      }
    )
  }

  closeAlert()
  {
    this.isSubmitted = true;
  }

  }

