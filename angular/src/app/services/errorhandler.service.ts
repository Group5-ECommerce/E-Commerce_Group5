import { ErrorHandler, Injectable } from '@angular/core';
import { MyMonitoringService } from 'src/logging.service';

@Injectable({
  providedIn: 'root'
})
export class ErrorhandlerService extends ErrorHandler{
  loggingService: any;

  constructor(private myMonitoringService: MyMonitoringService) {
    super();
   }

   override handleError(error: Error){
    this.loggingService.logException(error);
   }
}
