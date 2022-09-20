import { TestBed } from '@angular/core/testing';
import { OktaAuth } from '@okta/okta-auth-js';

import { AuthInterceptor } from './auth.interceptor';


describe('AuthInterceptor', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      AuthInterceptor,
      OktaAuth
      ]
  }));

  it('should be created', () => {
    const interceptor: AuthInterceptor = TestBed.inject(AuthInterceptor);
    expect(interceptor).toBeTruthy();
  });
});

