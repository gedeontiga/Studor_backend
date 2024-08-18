import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SendNotesreportService } from '../send-notesreport.service';

declare var bootstrap: any;

@Component({
  selector: 'app-notesreport-form',
  templateUrl: './notesreport-form.component.html',
  styleUrl: './notesreport-form.component.css'
})
export class NotesreportFormComponent implements OnInit {
  averageValid: boolean = true
  ableToBeSubmit: boolean = true
  notesForm: FormGroup;
  levels: any[] = []
  options: any[] = []
  subjects: any[] = []
  average: any = 0;
  mention: string = "";
  coefs: Map<string, number> = new Map<string, number>()

  // @Output() formSubmit = new EventEmitter<any>();

  constructor(private fb: FormBuilder, private sendNotesreportService: SendNotesreportService) {
    this.notesForm =  this.createForm()
  }

  createForm(){
    this.subjects = []
    this.notesForm = this.fb.group({
      option: ['', Validators.required],
      niveau: ['', Validators.required],
      notes: this.fb.group({})
    })
    return this.notesForm
  }


  ngOnInit(): void {
    this.sendNotesreportService.getAllOption().subscribe(options => {
      Object.values(options).forEach(option => {
        this.options.push(option)
      })
    })
  }

  dismissPopup(){
      const modal = bootstrap.Modal.getInstance(document.getElementById('notesModal'));
      modal?.hide();
  }

  onOptionChange(){
    this.subjects = []
    const optionValue = this.notesForm.get('option')?.value;

    this.sendNotesreportService.getAllLevel(optionValue).subscribe((levels) => {
      this.levels = new Array();
      Object.values(levels).forEach(level => {
        this.levels.push(level);
        this.notesForm.setControl('niveau', this.fb.control('', Validators.required));
      })
    })
  }

  onLevelChange(){
    const levelValue = this.notesForm.get('niveau')?.value;
    const notesGroup = this.notesForm.get('notes') as FormGroup;
    this.sendNotesreportService.getAllCoef(levelValue).subscribe(coefs => {
      this.coefs.clear();
      this.coefs = new Map<string, number>(Object.entries(coefs) as [string, number][])
      this.subjects = Array.from(this.coefs.keys())
      this.subjects.forEach(subject => {
        notesGroup.addControl(subject, this.fb.control(null, [Validators.required, Validators.min(0), Validators.max(20)]))
      })
    })
  }

  checkAverage(notesReport: any) {
    let totalNotes = 0;
    let totalCoefficients = 0;

    this.subjects.forEach(subject =>{
        if(notesReport['notes'][subject] !== null){
          totalNotes += notesReport['notes'][subject] * Number(this.coefs.get(subject))
          totalCoefficients += Number(this.coefs.get(subject))
        }
      }
    )
    const average = totalNotes / totalCoefficients;
    this.average = average;
    if(this.average < 12.00 && this.average >= 10.00){
      this.mention = "Passable";
    }
    else if(this.average < 14.00 && this.average >= 12.00){
        this.mention = "Assez Bien";
    }
    else if(this.average < 16.00 && this.average >= 14.00){
        this.mention = "Bien";
    }
    else if(this.average < 18.00 && this.average >= 16.00){
        this.mention = "Tres Bien";
    }
    else if(this.average >= 18.00){
        this.mention = "Excellent";
    }
    this.averageValid = (average >= 10 && average < 20);
  }

  checkAllHaveBeenFilling(form: any){
    if (form['option'] === '' || form['niveau'] === '') {
      return this.ableToBeSubmit = false
    }
    for (const subject of this.subjects){

      if(form['notes'][subject] === null){
        return this.ableToBeSubmit = false
      }
    }
    return this.ableToBeSubmit = true
  }

  onSubmit() {
    const formValues = this.notesForm.value;
    this.checkAllHaveBeenFilling(formValues)
    console.log(this.ableToBeSubmit);
    this.checkAverage(formValues)
    console.log(this.averageValid);

    if(this.notesForm.valid && this.averageValid){
      const averageAndMention = {
        average: this.average,
        mention: this.mention
      }
      const notesReportForm = {
        ...formValues,
        ...averageAndMention
      }
      this.sendNotesreportService.saveNotes(notesReportForm).subscribe(
        response => {
          console.log(response);

        }
      );
      this.dismissPopup();
    }
  }
}
