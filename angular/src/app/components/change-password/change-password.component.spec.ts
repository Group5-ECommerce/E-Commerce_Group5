import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { ChangePasswordComponent } from './change-password.component';
import { OKTA_AUTH } from '@okta/okta-angular';
import { UserDetailsService } from 'src/app/services/user-details.service';

describe('ChangePasswordComponent', () => {
  let component: ChangePasswordComponent;
  let fixture: ComponentFixture<ChangePasswordComponent>;
  const authSpy = jasmine.createSpyObj('OktaAuth', ['login']);

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [HttpClient, 
        { provide: OKTA_AUTH, useValue: authSpy },
      UserDetailsService],
      declarations: [ ChangePasswordComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChangePasswordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
