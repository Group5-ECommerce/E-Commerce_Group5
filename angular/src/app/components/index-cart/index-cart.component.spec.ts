import { CommonModule } from '@angular/common';
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { OktaAuthStateService, OKTA_AUTH } from '@okta/okta-angular';
import { NgxPaginationModule } from 'ngx-pagination';
import { of } from 'rxjs';
import { IndexedDatabase } from 'src/app/indexeddb';
import { IndexCartComponent } from './index-cart.component';

describe('IndexCartComponent', () => {
  let component: IndexCartComponent;
  let fixture: ComponentFixture<IndexCartComponent>;
  const authSpy = jasmine.createSpyObj('OktaAuth', ['login']);
  const authStateSpy = jasmine.createSpyObj('OktaAuthStateService', [], {
    authState$: of({
      isAuthenticated: false
    })
  });
  
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CommonModule, NgxPaginationModule],
      providers: [IndexedDatabase, { provide: OKTA_AUTH, useValue: authSpy },
        { provide: OktaAuthStateService, useValue: authStateSpy }
      ],
      declarations: [ IndexCartComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(IndexCartComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  //it(`should start on page 1`, async(() => {
  //  expect(component.pageNum).toEqual(1);
  //}));
});