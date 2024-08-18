import { TestBed } from '@angular/core/testing';

import { EstablishmentSuggestionService } from './establishment-suggestion.service';

describe('EstablishmentSuggestionService', () => {
  let service: EstablishmentSuggestionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(EstablishmentSuggestionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
