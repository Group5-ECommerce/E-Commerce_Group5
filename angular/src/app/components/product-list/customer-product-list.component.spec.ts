import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CustomerProductListComponent } from './customer-product-list.component';
import { NgxPaginationModule } from 'ngx-pagination';

describe('ProductListComponent', () => {
  let component: CustomerProductListComponent;
  let fixture: ComponentFixture<CustomerProductListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CustomerProductListComponent ],
      imports: [HttpClientModule, NgxPaginationModule]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CustomerProductListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
