import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Address } from '../models/address/address';

@Injectable({
  providedIn: 'root'
})
export class AddressService {

  private addressUrl = environment.backendURL + "/listOfAddressById";

  constructor(private HttpClient: HttpClient) { }

  getAddressById()
  {
    return this.HttpClient.get<Address[]>(`${this.addressUrl}`);
  }

}
