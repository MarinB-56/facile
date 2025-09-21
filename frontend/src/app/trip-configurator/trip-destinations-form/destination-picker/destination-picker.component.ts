import { HttpClient } from '@angular/common/http';
import { Component, inject, input, signal, output, SimpleChanges, effect } from '@angular/core';
import { Destination } from '../../../models/destination.model';
import { debounceTime, distinctUntilChanged, filter, Subject, Subscription, switchMap } from 'rxjs';
import {FormControl, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatInputModule} from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatAutocompleteModule, MatAutocompleteSelectedEvent} from '@angular/material/autocomplete';

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
export class DestinationPickerComponent {
  // Réception des paramètres envoyés par le composant parent
  placeholderText = input();
  defaultDestination = input.required<Destination>();

  // Envoi de la destination choisie par l'utilisateur au composant parent
  selectedDestinationEvent = output<Destination>();

  // Paramètres pour l'appel API backend
  private http = inject(HttpClient);
  private urlAutoComplete = "http://localhost:8080/api/navitia/";
  private searchTerms = new Subject<string>();
  private searchSub: Subscription;

  // Form ulaire d'autocomplétion (Angular material)
  myControl = new FormControl('');
  autoCompleteResults = signal<Array<Destination>>([]); // Signifie que les objets dans le tableau auront, entre autres, un champ name

  constructor(){

    // utilisation de effect pour traquer le changement des destination
    // (lors du choix de l'utilisateur ou lors de l'inversion du départ / destination)
    effect(() => {
      const destination = this.defaultDestination();

      if(destination.name == "Auray (Auray)"){ // Si on va à belle ile
        // this.myControl.setValue("Belle-Ile-En-Mer");
        this.myControl.setValue(this.defaultDestination().name);
        this.myControl.disable();
      }else { // Si une destination par défaut a été précisée (autre que Belle ile)
        this.myControl.setValue(this.defaultDestination().name);
        this.myControl.enable();
      }
    })

    // Création d'un flux asynchrone pour l'autocomplete des destinations
    this.searchSub = this.searchTerms.pipe(
      debounceTime(400), // On attend 400 ms sans input avant de passer à la suite.
      filter((input: string) => input.length >= 3), // On filtre les input de moins de 3 char
      distinctUntilChanged(), // On ne fait rien si rien n'a changé dans l'input
      switchMap((input: string) => this.http.get<any>(`${this.urlAutoComplete}${encodeURIComponent(input)}`)) // Appel de l'url avec l'input
    ).subscribe(response => {
      this.fillAutocomplete(response); // Récupération et affichage des propositions de l'API (autocomplete)
    })
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
      this.selectedDestinationEvent.emit(selectedDestination);
    }
  }
}
