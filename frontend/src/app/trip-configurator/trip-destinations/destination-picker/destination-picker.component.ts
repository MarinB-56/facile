import { HttpClient } from '@angular/common/http';
import { Component, inject, Input, signal, HostListener, ElementRef, Output, EventEmitter, input } from '@angular/core';
import { fakeAsync } from '@angular/core/testing';
import { Destination } from '../../../models/destination.model';
import { debounce, debounceTime, distinctUntilChanged, filter, map, Observable, startWith, Subject, Subscription, switchMap } from 'rxjs';
import {FormControl, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatAutocompleteModule, MatAutocompleteSelectedEvent} from '@angular/material/autocomplete';
import { AsyncPipe } from '@angular/common';

@Component({
  selector: 'app-destination-picker',
  imports: [
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatAutocompleteModule,
    ReactiveFormsModule,
    AsyncPipe],
  templateUrl: './destination-picker.component.html',
  styleUrl: './destination-picker.component.scss'
})
export class DestinationPickerComponent {
  // Paramètres pour l'appel API backend
  private http = inject(HttpClient);
  private urlAutoComplete = "http://localhost:8080/api/navitia/";

  // Form autocomplete
  myControl = new FormControl('');
  autoCompleteResults = signal<Array<Destination>>([]); // Signifie que les objets dans le tableau auront, entre autres, un champ name

  @Output() selectedDestination = new EventEmitter<Destination>();
  @Input() placeholderText = "";

  private searchTerms = new Subject<string>();
  private searchSub: Subscription;

  // Création d'un flux asynchrone
  constructor(){
    this.searchSub = this.searchTerms.pipe(
      debounceTime(400), // On attend 400 ms sans input avant de passer à la suite.
      filter((input: string) => input.length >= 3), // On filtre les input de moins de 3 char
      distinctUntilChanged(), // On ne fait rien si rien n'a changé dans l'input
      switchMap((input: string) => this.http.get<any>(`${this.urlAutoComplete}${encodeURIComponent(input)}`)) // Appel de l'url avec l'input
    ).subscribe(response => {
      this.fillAutocomplete(response);
    })
  }

  onInput(event :Event) :void {
    const input = event.target as HTMLInputElement;

    // Déclenchement de l'appel API
    if(input.value !== ''){
      this.searchTerms.next(input.value);
    }else {
      this.autoCompleteResults.set([]);
      // this.isDropdownOpen.set(false);
    }
  }

  fillAutocomplete(response: any){
    if(response.places != null){
      this.autoCompleteResults.set(response.places);
    }else {
      this.autoCompleteResults.set([]);
    }
  }

  checkOptionSelected(event : MatAutocompleteSelectedEvent) {
    const selectedValue = event.option.value;
    const selectedDestination = this.autoCompleteResults().find(destination => destination.name === selectedValue);

    if(selectedDestination){
      // On envoie l'objet sélectionné par l'utilisateur au composant parent
      this.selectedDestination.emit(selectedDestination);
    }
  }
}
