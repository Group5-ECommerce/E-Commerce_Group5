import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { NgxPaginationModule } from 'ngx-pagination';
import { OrderTrackComponent } from './order-track.component';
import { FormsModule } from '@angular/forms';

describe('OrderTrackComponent', () => {
  let component: OrderTrackComponent;
  let fixture: ComponentFixture<OrderTrackComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OrderTrackComponent ],
      imports: [HttpClientModule, NgxPaginationModule, FormsModule]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrderTrackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
