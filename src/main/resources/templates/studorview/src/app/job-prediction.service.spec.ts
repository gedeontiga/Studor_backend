import { TestBed } from '@angular/core/testing';

import { JobPredictionService } from './job-prediction.service';

describe('JobPredictionService', () => {
  let service: JobPredictionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(JobPredictionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
