import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Address } from '../models/address/address';

@Injectable({
  providedIn: 'root'
})
export class AddressService {

  private addressUrl = 'http://localhost:8080/listOfAddressById'

  constructor(private HttpClient: HttpClient) { }

  getAddressById()
  {
    return this.HttpClient.get<Address[]>(`${this.addressUrl}`);
  }

}
