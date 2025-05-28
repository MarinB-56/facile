import { HttpClient } from '@angular/common/http';
import { Component, inject, Input } from '@angular/core';
import { fakeAsync } from '@angular/core/testing';

@Component({
  selector: 'app-destination-picker',
  imports: [],
  templateUrl: './destination-picker.component.html',
  styleUrl: './destination-picker.component.css'
})
export class DestinationPickerComponent {
  private http = inject(HttpClient);
  private urlAutoComplete = "http://localhost:8080/api/navitia/";

  autoCompleteResults : Array<{name: string}> = []; // Signifie que les objets dans le tableau auront, entre autres, un champ name
  // autoCompleteResults : String[] = [];

  @Input() placeholderText = "";
  @Input() valueText = "";
  @Input() isReadOnly :boolean = false; // Par défaut, input modifiable
  @Input() isDisabled :boolean = false;

  autoComplete(event :Event) :void {
    const input = event.target as HTMLInputElement;
    const value = input.value;
    console.log(`Value ${value}`);

    if(value !== ''){
      this.http
      .get<any>(`${this.urlAutoComplete}${value}`)
      .subscribe(response => {
        // console.log(response.places)
        if(response.places != null){
          this.autoCompleteResults = response.places;
        }else {
          this.autoCompleteResults = [];
        }

        console.log(this.autoCompleteResults);
      });
    }else {
      this.autoCompleteResults = [];
    }
  }

  displayProposals(){
    //Insérer les propositions dans le dom
  }
}
