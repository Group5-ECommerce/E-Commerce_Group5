import { ComponentFixture, TestBed, async} from '@angular/core/testing';
import { ProductService } from 'src/app/services/product.service';
import { HttpClientModule, HttpClient } from '@angular/common/http';

import { CustomerProductListComponent } from './customer-product-list.component';
import { IndexedDatabase } from 'src/app/indexeddb';
import { OktaAuthStateService } from '@okta/okta-angular';
import { OktaAuth } from '@okta/okta-auth-js';

describe('ProductListComponent', () => {
  let component: CustomerProductListComponent;
  let fixture: ComponentFixture<CustomerProductListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [HttpClient, IndexedDatabase, OktaAuth],
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

  it(`should have multiple products`, async(() => {
    expect(component.products?.length).toBeGreaterThanOrEqual(1);
  }));
});
