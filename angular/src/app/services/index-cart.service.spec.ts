import { TestBed } from '@angular/core/testing';
import { OktaAuth } from '@okta/okta-auth-js';
import { IndexedDatabase } from 'src/app/indexeddb';
import { IndexCartService } from './index-cart.service';

describe('IndexCartService', () => {
  let service: IndexCartService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [IndexedDatabase, OktaAuth]
    });
    service = TestBed.inject(IndexCartService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});