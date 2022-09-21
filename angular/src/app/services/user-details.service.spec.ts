import { TestBed } from '@angular/core/testing';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { UserDetailsService } from './user-details.service';
import { OKTA_AUTH } from '@okta/okta-angular';

describe('UserDetailsServiceService', () => {
  let service: UserDetailsService;
  const authSpy = jasmine.createSpyObj('OktaAuth', ['login']);
  const httpClientSpy = jasmine.createSpyObj('HttpClient', ['post', 'get'])

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [
        HttpClientModule],
      providers: [{provide: HttpClient, useValue: httpClientSpy}, 
        { provide: OKTA_AUTH, useValue: authSpy }]
    });
    service = TestBed.inject(UserDetailsService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
