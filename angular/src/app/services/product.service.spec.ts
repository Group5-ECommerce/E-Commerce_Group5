import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpClientModule, HttpClient } from '@angular/common/http';
import { Product } from '../models/product.model';
import { ProductService } from './product.service';

describe('ProductService', () => {
  let product: Product
  let service: ProductService;
  let fixture: ComponentFixture<Product>

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [HttpClient]
    });

    service = TestBed.inject(ProductService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  //it(`html should render one product`, async(() => {
  //  fixture.detectChanges();
  //  const el = fixture.nativeElement.querySelector('p');
 //   expect(el.innterText).toContain('Laptop')
 // }));
});
