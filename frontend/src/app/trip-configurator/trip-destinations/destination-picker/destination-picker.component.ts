import { HttpClient } from '@angular/common/http';
import { Component, inject, Input, signal, HostListener, ElementRef, Output, EventEmitter } from '@angular/core';
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

  autoCompleteResults = signal<Array<{ name: string, embedded_type: string }>>([]); // Signifie que les objets dans le tableau auront, entre autres, un champ name
  isDropdownOpen = signal(false);

  @Input() placeholderText = "";
  @Input() valueText: string = "";
  @Input() isReadOnly :boolean = false; // Par défaut, input modifiable
  @Input() isDisabled :boolean = false;

  @Output() selectedDestination = new EventEmitter<{ name: string, embedded_type: string }>();

  autoComplete(event :Event) :void {
    const input = event.target as HTMLInputElement;
    this.valueText = input.value;

    if(this.valueText !== ''){
      this.http
        .get<any>(`${this.urlAutoComplete}${this.valueText}`)
        .subscribe(response => {
          this.displayProposals(response);
        });
    }else {
      this.autoCompleteResults.set([]);
      this.isDropdownOpen.set(false);
    }
  }

  displayProposals(response: any){
    if(response.places != null){
      this.isDropdownOpen.set(true);
      this.autoCompleteResults.set(response.places);
    }else {
      this.autoCompleteResults.set([]);
      this.isDropdownOpen.set(false);
    }
  }

  focus(event: Event){
    // this.isDropdownOpen.set(true);
  }

  essai(event: Event){
    console.log(`Value: ${this.valueText}`);
    this.isDropdownOpen.set(false);
    this.valueText = "";
  }

  chooseDestination(proposal: { name: string, embedded_type: string }){
    // Définir la valeur de l'input avec l'élément cliqué
    console.log(proposal);
    this.valueText = proposal.name; // Affuchage du choix à l'utilisateur (update de l'input)
    this.selectedDestination.emit(proposal); // Transmettre la valeur sélectionnée au composant parent
  }
}
