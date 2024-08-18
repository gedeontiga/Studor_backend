import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SendNotesreportService {

  readonly NOTESREPORT_API_URL = "http://localhost:8080/notesreport-api"

  readonly ENDPOINT_ALL_OPTION = "/all-option"

  readonly ENDPOINT_ALL_LEVEL = "/all-level"

  readonly ENDPOINT_ALL_COEF = "/all-coef"

  readonly ENDPOINT_SEND_NOTES = "/send-notes"

  constructor(private http: HttpClient) { }

  getAllOption(){
    return this.http.get(this.NOTESREPORT_API_URL + this.ENDPOINT_ALL_OPTION)
  }

  getAllLevel(optionName: String){
    return this.http.get(this.NOTESREPORT_API_URL + this.ENDPOINT_ALL_LEVEL + "/" + optionName)
  }

  getAllCoef(codeLevel: String){
    return this.http.get(this.NOTESREPORT_API_URL + this.ENDPOINT_ALL_COEF + "/" + codeLevel)
  }

  saveNotes(data: any) {
    return this.http.post(this.NOTESREPORT_API_URL+this.ENDPOINT_SEND_NOTES, data, { headers: { 'Content-Type': 'application/json' } });
  }
}
