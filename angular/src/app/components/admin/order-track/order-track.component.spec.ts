import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { OrderTrackComponent } from './order-track.component';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

describe('OrderTrackComponent', () => {
  let component: OrderTrackComponent;
  let fixture: ComponentFixture<OrderTrackComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, FormsModule, RouterModule.forRoot([])],
      providers: [HttpClient],
      declarations: [OrderTrackComponent]
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
