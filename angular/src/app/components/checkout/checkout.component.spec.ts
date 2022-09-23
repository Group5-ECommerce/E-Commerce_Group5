import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { CheckoutComponent } from './checkout.component';
import { IndexedDatabase } from 'src/app/indexeddb';
import { OktaAuthStateService, OKTA_AUTH } from '@okta/okta-angular';
import { of } from 'rxjs';
import { FormsModule } from '@angular/forms';

describe('CheckoutComponent', () => {
  let component: CheckoutComponent;
  let fixture: ComponentFixture<CheckoutComponent>;
  const authSpy = jasmine.createSpyObj('OktaAuth', ['login']);
  const authStateSpy = jasmine.createSpyObj('OktaAuthStateService', [], {
    authState$: of({
      isAuthenticated: false
    })
  });
  
  beforeEach(async () => {
    window.Stripe = function () {
      // your mock here
      return {
        elements: () => ({
          create: () => ({
            mount: () => ({ /* your card */ })
          })
        })
      }
    }

    await TestBed.configureTestingModule({
      imports: [HttpClientModule, FormsModule],
      providers: [HttpClient, IndexedDatabase, 
        { provide: OKTA_AUTH, useValue: authSpy },
        { provide: OktaAuthStateService, useValue: authStateSpy }
      ],
      declarations: [ CheckoutComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CheckoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
