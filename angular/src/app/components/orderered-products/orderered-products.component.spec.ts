import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrdereredProductsComponent } from './orderered-products.component';

describe('OrdereredProductsComponent', () => {
  let component: OrdereredProductsComponent;
  let fixture: ComponentFixture<OrdereredProductsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrdereredProductsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrdereredProductsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
