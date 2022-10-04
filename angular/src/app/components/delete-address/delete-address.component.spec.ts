import { HttpClient, HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';

import { DeleteAddressComponent } from './delete-address.component';

describe('DeleteAddressComponent', () => {
  let component: DeleteAddressComponent;
  let fixture: ComponentFixture<DeleteAddressComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule, FormsModule ],
      providers: [HttpClient],
      declarations: [ DeleteAddressComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DeleteAddressComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
