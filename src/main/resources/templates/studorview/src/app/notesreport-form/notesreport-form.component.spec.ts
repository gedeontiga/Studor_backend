import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NotesreportFormComponent } from './notesreport-form.component';

describe('NotesreportFormComponent', () => {
  let component: NotesreportFormComponent;
  let fixture: ComponentFixture<NotesreportFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NotesreportFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NotesreportFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
