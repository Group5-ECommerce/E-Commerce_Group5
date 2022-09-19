import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from "@angular/router/testing";
import { OrdereredProductsComponent } from './orderered-products.component';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { NgxPaginationModule } from 'ngx-pagination';

describe('OrdereredProductsComponent', () => {
  let component: OrdereredProductsComponent;
  let fixture: ComponentFixture<OrdereredProductsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrdereredProductsComponent ],
      imports: [HttpClientModule, NgxPaginationModule, RouterTestingModule]
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
