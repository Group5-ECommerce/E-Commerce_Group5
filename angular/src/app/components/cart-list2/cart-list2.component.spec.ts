import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CartList2Component } from './cart-list2.component';

describe('CartList2Component', () => {
  let component: CartList2Component;
  let fixture: ComponentFixture<CartList2Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CartList2Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CartList2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
