import { Component, OnInit } from '@angular/core';
import { Address } from 'src/app/models/address/address';
import { AddressService } from 'src/app/services/address.service';

@Component({
  selector: 'app-update-address',
  templateUrl: './update-address.component.html',
  styleUrls: ['./update-address.component.css']
})
export class UpdateAddressComponent implements OnInit {

  updatedAddress = new Address()
  oldAddress = new Address()
  userAddress: Address[]
  isSubmitted = false
  id: number
  constructor(private addressService: AddressService) { }

  ngOnInit(): void {
    this.getUserAddress()
  }

  updateAddress()
  {
    this.id = this.updatedAddress.addressId
    this.addressService.updateAddress(this.id , this.updatedAddress).subscribe(
      {
        next: (res) => 
        {
          console.log(res);
          this.isSubmitted = true;
        }
      }
    )
  }

  getUserAddress()
  {
    this.addressService.getAddressById().subscribe((response) =>
    {
      this.userAddress = response;
      console.log(this.userAddress);
    })
  }

  onSelectAddress()
  {
      //this.billingAddressId = a.value;
      let i = (document.getElementById('addressSelect') as HTMLInputElement).value;
      this.updatedAddress= this.userAddress[i] 
      console.log(this.oldAddress);
  }

  closeAlert()
  {
    this.isSubmitted = false;
  }

}
