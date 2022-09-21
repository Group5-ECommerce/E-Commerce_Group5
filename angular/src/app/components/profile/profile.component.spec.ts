import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { OktaAuthStateService } from '@okta/okta-angular';
import { of } from 'rxjs';

import { ProfileComponent } from './profile.component';

describe('ProfileComponent', () => {
  
  let component: ProfileComponent;
  let fixture: ComponentFixture<ProfileComponent>;

  const authSpy = jasmine.createSpyObj('OktaAuthStateService', [], {
    authState$: of({
      isAuthenticated: true,
      idToken: {
        claims: {
          name
        }
      }
    })
  });

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProfileComponent ],
      providers: [{ provide: OktaAuthStateService, useValue: authSpy }]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
