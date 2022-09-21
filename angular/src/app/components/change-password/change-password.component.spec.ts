import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { ChangePasswordComponent } from './change-password.component';
import { OktaAuth } from '@okta/okta-auth-js';

describe('ChangePasswordComponent', () => {
  let component: ChangePasswordComponent;
  let fixture: ComponentFixture<ChangePasswordComponent>;
  let mockOktaAuth;
  let httpClientSpy = jasmine.createSpyObj('HttpClient', ['post', 'get'])
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [{provide: HttpClient, useValue: httpClientSpy}, 
                  {provide: OktaAuth, useClass: mockOktaAuth}],
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
