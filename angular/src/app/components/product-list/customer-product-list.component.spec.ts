import { ComponentFixture, TestBed, async} from '@angular/core/testing';
import { ProductService } from 'src/app/services/product.service';
import { HttpClientModule, HttpClient } from '@angular/common/http';

import { CustomerProductListComponent } from './customer-product-list.component';
import { IndexedDatabase } from 'src/app/indexeddb';
import { OktaAuthStateService, OKTA_AUTH } from '@okta/okta-angular';
import { OktaAuth } from '@okta/okta-auth-js';
import { of } from 'rxjs';
import { CommonModule } from '@angular/common';
import { NgxPaginationModule } from 'ngx-pagination';

describe('ProductListComponent', () => {
  let component: CustomerProductListComponent;
  let fixture: ComponentFixture<CustomerProductListComponent>;
  const authSpy = jasmine.createSpyObj('OktaAuth', ['login']);
  const authStateSpy = jasmine.createSpyObj('OktaAuthStateService', [], {
    authState$: of({
      isAuthenticated: false
    })
  });
  
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, CommonModule, NgxPaginationModule],
      providers: [HttpClient, IndexedDatabase, { provide: OKTA_AUTH, useValue: authSpy },
        { provide: OktaAuthStateService, useValue: authStateSpy }],
      declarations: [ CustomerProductListComponent ],
    })
    .compileComponents();

    fixture = TestBed.createComponent(CustomerProductListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  /* it(`should have multiple products`, async(() => {
    expect(component.products?.length).toBeGreaterThanOrEqual(1);
  })); */
});
