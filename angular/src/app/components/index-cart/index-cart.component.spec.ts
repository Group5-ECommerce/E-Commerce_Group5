import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { OktaAuth } from '@okta/okta-auth-js';
import { IndexedDatabase } from 'src/app/indexeddb';
import { IndexCartComponent } from './index-cart.component';

describe('IndexCartComponent', () => {
  let component: IndexCartComponent;
  let fixture: ComponentFixture<IndexCartComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      providers: [IndexedDatabase, OktaAuth],
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