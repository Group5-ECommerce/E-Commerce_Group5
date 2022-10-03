import { TestBed } from '@angular/core/testing';

import { ErrorhandlerService } from './errorhandler.service';
import { MyMonitoringService } from './logging.service';

describe('ErrorhandlerService', () => {
  let service: ErrorhandlerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [MyMonitoringService]
    });
    service = TestBed.inject(ErrorhandlerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
