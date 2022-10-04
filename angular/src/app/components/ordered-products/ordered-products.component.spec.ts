import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterModule } from '@angular/router';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { OrderedProductsComponent } from './ordered-products.component';
import { CommonModule } from '@angular/common';
import { NgxPaginationModule } from 'ngx-pagination';
import { OKTA_AUTH } from '@okta/okta-angular';

describe('OrderedProductsComponent', () => {
  let component: OrderedProductsComponent;
  let fixture: ComponentFixture<OrderedProductsComponent>;
  const authSpy = jasmine.createSpyObj('OktaAuth', ['login']);

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        HttpClientModule,
        CommonModule,
        NgxPaginationModule,
        RouterModule.forRoot([])],
      providers: [HttpClient,
        { provide: OKTA_AUTH, useValue: authSpy }],
      declarations: [ OrderedProductsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrderedProductsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
