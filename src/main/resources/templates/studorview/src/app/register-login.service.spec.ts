import { TestBed } from '@angular/core/testing';

import { RegisterLoginService } from './register-login.service';

describe('RegisterLoginService', () => {
  let service: RegisterLoginService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RegisterLoginService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
