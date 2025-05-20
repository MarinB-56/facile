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
  private urlAutoComplete = "https://dictionary.lewagon.com/autocomplete/";

  autoCompleteResults : String[] = [];

  @Input() placeholderText = "";
  @Input() valueText = "";
  @Input() isReadOnly :boolean = false; // Par défaut, input modifiable
  @Input() isDisabled :boolean = false;

  autoComplete(event :Event) :void {
    const input = event.target as HTMLInputElement;
    const value = input.value;
    console.log(value);

    this.http
      .get<any>(`${this.urlAutoComplete}${value}`)
      .subscribe(response => {
        if(response.words != undefined) {
          this.autoCompleteResults = response.words.slice(0, 5);
        } else {
          this.autoCompleteResults = [];
        }
      });
  }

  displayProposals(){
    //Insérer les propositions dans le dom
  }
}
