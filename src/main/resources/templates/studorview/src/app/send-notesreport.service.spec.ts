import { TestBed } from '@angular/core/testing';

import { SendNotesreportService } from './send-notesreport.service';

describe('SendNotesreportService', () => {
  let service: SendNotesreportService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SendNotesreportService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
