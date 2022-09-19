import { TestBed } from '@angular/core/testing';
import { HttpClientModule } from '@angular/common/http';
import { AddtocartserviceService } from './addtocartservice.service';

describe('AddtocartserviceService', () => {
  let service: AddtocartserviceService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
      providers: [AddtocartserviceService]
    });
    service = TestBed.inject(AddtocartserviceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
