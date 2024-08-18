import { ComponentFixture, TestBed } from '@angular/core/testing';

import { JobEstablishmentComponent } from './job-establishment.component';

describe('JobEstablishmentComponent', () => {
  let component: JobEstablishmentComponent;
  let fixture: ComponentFixture<JobEstablishmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [JobEstablishmentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(JobEstablishmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
