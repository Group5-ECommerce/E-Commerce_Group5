import { TestBed } from '@angular/core/testing';

import { IndexCartService } from './index-cart.service';

describe('Cart2Service', () => {
  let service: IndexCartService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(IndexCartService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});