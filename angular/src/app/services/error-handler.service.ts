import { ErrorHandler, Injectable } from '@angular/core';
import { MyMonitoringService } from 'src/app/services/logging-service';

@Injectable({
  providedIn: 'root'
})
export class ErrorHandlerService extends ErrorHandler{
  loggingService: any;

  constructor(private myMonitoringService: MyMonitoringService) {
    super();
   }

   override handleError(error: Error){
    this.loggingService.logException(error);
   }
}