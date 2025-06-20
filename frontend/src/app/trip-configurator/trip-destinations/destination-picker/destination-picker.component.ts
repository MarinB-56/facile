import { HttpClient } from '@angular/common/http';
import { Component, inject, Input, signal, HostListener, ElementRef, Output, EventEmitter, input, OnInit, SimpleChanges } from '@angular/core';
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
    ReactiveFormsModule
],
  templateUrl: './destination-picker.component.html',
  styleUrl: './destination-picker.component.scss'
})
export class DestinationPickerComponent implements OnInit {
  // Paramètres pour l'appel API backend
  private http = inject(HttpClient);
  private urlAutoComplete = "http://localhost:8080/api/navitia/";

  // Envoi de la destination choisie par l'utilisateur au composant parent
  @Output() selectedDestination = new EventEmitter<Destination>();

  // Réception des paramètres par le composant parent
  @Input() direction ="";
  @Input() placeholderText = "";
  @Input() defaultDestination :Destination = {name: "", embedded_type: "", id: ""};

  // Form autocomplete
  myControl = new FormControl('');
  autoCompleteResults = signal<Array<Destination>>([]); // Signifie que les objets dans le tableau auront, entre autres, un champ name

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

  ngOnInit(): void {
    if(this.defaultDestination.name == "Quiberon (Quiberon)"){ // Si on va à belle ile
      this.myControl.setValue("Belle-Ile-En-Mer");
      this.myControl.disable();
    }else if(this.defaultDestination.name !== ""){ // Si une destination par défaut a été précisée (autre que Belle ile)
      this.myControl.setValue(this.defaultDestination.name);
      this.myControl.disable();
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['defaultDestination']) {
      const updatedDestination: Destination = changes['defaultDestination'].currentValue;

      this.myControl.setValue(updatedDestination.name);
      this.myControl.enable();

      if(updatedDestination.name === "Quiberon (Quiberon)") this.myControl.disable();
    }
  }

  onInput(event :Event) :void {
    const input = event.target as HTMLInputElement;

    // Déclenchement de l'appel API
    if(input.value !== ''){
      this.searchTerms.next(input.value);
    }else {
      this.autoCompleteResults.set([]);
    }
  }

  fillAutocomplete(response: any){
    if(response.places != null){
      this.autoCompleteResults.set(response.places);
    }else {
      this.autoCompleteResults.set([]);
    }
  }

  // Vérification de la valeur cliquée par l'utilisateur dans l'autocomplete
  checkOptionSelected(event : MatAutocompleteSelectedEvent) {
    const selectedValue = event.option.value;
    const selectedDestination = this.autoCompleteResults().find(destination => destination.name === selectedValue);

    // Si la valeur n'est pas vide
    if(selectedDestination){
      // On envoie l'objet sélectionné par l'utilisateur au composant parent
      console.log("Composant destination-picker envoie la destination à trip-destination");
      this.selectedDestination.emit(selectedDestination);
    }
  }
}
