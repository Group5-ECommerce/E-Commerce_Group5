import { ComponentFixture, TestBed, async} from '@angular/core/testing';
import { ProductService } from 'src/app/services/product.service';

import { CustomerProductListComponent } from './customer-product-list.component';

describe('ProductListComponent', () => {
  let component: CustomerProductListComponent;
  let fixture: ComponentFixture<CustomerProductListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
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
