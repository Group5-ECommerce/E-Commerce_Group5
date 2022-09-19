import { TestBed } from '@angular/core/testing';
import { Inject, Injectable } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { UserDetailsService } from './user-details.service';
import { OktaAuthGuard } from '@okta/okta-angular';
import { NgModule } from '@angular/core';
import { OktaAuth } from '@okta/okta-auth-js';
import { OKTA_AUTH } from '@okta/okta-angular';

describe('UserDetailsServiceService', () => {
  let service: UserDetailsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      //declarations: [OktaAuthGuard],
      imports: [HttpClientModule, OktaAuth, OKTA_AUTH],
      providers: [UserDetailsService, OktaAuthGuard]});
    service = TestBed.inject(UserDetailsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
