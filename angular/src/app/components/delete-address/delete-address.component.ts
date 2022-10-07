import { Component, OnInit } from '@angular/core';
import { Address } from 'src/app/models/address/address';
import { AddressService } from 'src/app/services/address.service';

@Component({
  selector: 'app-delete-address',
  templateUrl: './delete-address.component.html',
  styleUrls: ['./delete-address.component.css']
})
export class DeleteAddressComponent implements OnInit {

  address = new Address()
  userAddress: Address[]
  isSubmitted = false
  id: number
  constructor(private addressService: AddressService) { }

  ngOnInit(): void {
    this.getUserAddress()
  }


  deleteAddress()
  {
    this.id = this.address.addressId
    this.addressService.deleteAddress(this.id).subscribe((response) =>
    {
      console.log("Deleted");
      this.isSubmitted = true;
    })

  }
 
  getUserAddress()
  {
    this.addressService.getAddressByAccount().subscribe((response) =>
    {
      this.userAddress = response;
      console.log(this.userAddress);
    })
  }

  onSelectAddress()
  {
      let i = (document.getElementById('addressSelect') as HTMLInputElement).value;
      this.address= this.userAddress[i] 
  }

  closeAlert()
  {
    this.isSubmitted = false;
  }
}
