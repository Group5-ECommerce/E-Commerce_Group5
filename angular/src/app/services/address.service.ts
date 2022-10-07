import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Address } from '../models/address/address';

@Injectable({
  providedIn: 'root'
})
export class AddressService {

  private addressUrl = environment.backendURL + "/myAddress";

  private updateAddressUrl = environment.backendURL+ "/updateAddress";
  private deleteAddressUrl = environment.backendURL+ "/deleteAddress";

  constructor(private HttpClient: HttpClient) { }

  getAddressByAccount()
  {
    return this.HttpClient.get<Address[]>(`${this.addressUrl}`);
  }

  addAddress(data:any)
  {
    return this.HttpClient.post(environment.backendURL+ "/addAddress" , data);

  }

  updateAddress(id: any , data: any )
  {
    return this.HttpClient.put(`${this.updateAddressUrl}/${id}`, data);
  }

  deleteAddress(id: any)
  {
    return this.HttpClient.delete(`${this.deleteAddressUrl}/${id}`);
  }
}
