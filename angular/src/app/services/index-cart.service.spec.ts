import { TestBed } from '@angular/core/testing';
import { OktaAuthStateService, OKTA_AUTH } from '@okta/okta-angular';
import { of } from 'rxjs';
import { IndexedDatabase } from 'src/app/indexeddb';
import { IndexCartService } from './index-cart.service';

describe('IndexCartService', () => {
  let service: IndexCartService;
  const authStateSpy = jasmine.createSpyObj('OktaAuthStateService', [], {
    authState$: of({
      isAuthenticated: false
    })
  });
  const authSpy = jasmine.createSpyObj('OktaAuth', ['login']);

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [IndexedDatabase, 
        { provide: OKTA_AUTH, useValue: authSpy },
        { provide: OktaAuthStateService, useValue: authStateSpy },
      ]
    });
    service = TestBed.inject(IndexCartService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});