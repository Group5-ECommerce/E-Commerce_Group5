import { HttpClient } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { OktaAuthStateService, OKTA_AUTH } from '@okta/okta-angular';
import { of } from 'rxjs';
import { AppComponent } from './app.component';
import { IndexedDatabase } from './indexeddb';

describe('AppComponent', () => {
  beforeEach(async () => {
    const authStateSpy = jasmine.createSpyObj('OktaAuthStateService', [], {
      authState$: of({
        isAuthenticated: false
      })
    });
  
    const authSpy = jasmine.createSpyObj('OktaAuth', ['login'], {
      isLoginRedirect: () => {return false}
    });
    const httpClientSpy = jasmine.createSpyObj('HttpClient', ['post', 'get'])

    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule
      ],
      declarations: [
        AppComponent
      ],
      providers: [
        { provide: OktaAuthStateService, useValue: authStateSpy },
        { provide: OKTA_AUTH, useValue: authSpy },
        {provide: HttpClient, useValue: httpClientSpy},
        IndexedDatabase
      ]
    }).compileComponents();
  });

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'angular'`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('angular');
  });

  /*
  it('should render title', () => {
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;
    expect(compiled.querySelector('.content span')?.textContent).toContain('angular app is running!');
  });
  */
});
