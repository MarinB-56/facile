import { HttpClient } from '@angular/common/http';
import { Component, inject, Input, signal, HostListener, ElementRef, Output, EventEmitter, input } from '@angular/core';
import { fakeAsync } from '@angular/core/testing';
import { Destination } from '../../../models/destination.model';
import { debounce, debounceTime, distinctUntilChanged, filter, Subject, Subscription, switchMap } from 'rxjs';

@Component({
  selector: 'app-destination-picker',
  imports: [],
  templateUrl: './destination-picker.component.html',
  styleUrl: './destination-picker.component.css'
})
export class DestinationPickerComponent {
  private http = inject(HttpClient);
  private urlAutoComplete = "http://localhost:8080/api/navitia/";

  autoCompleteResults = signal<Array<Destination>>([]); // Signifie que les objets dans le tableau auront, entre autres, un champ name
  isDropdownOpen = signal(false);

  @Input() placeholderText = "";
  @Input() valueText: string = "";
  @Input() isReadOnly :boolean = false; // Par défaut, input modifiable
  @Input() isDisabled :boolean = false;

  @Output() selectedDestination = new EventEmitter<Destination>();

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
      this.displayProposals(response);
    })
  }

  autoComplete(event :Event) :void {
    const input = event.target as HTMLInputElement;
    this.valueText = input.value;

    if(this.valueText !== ''){
      this.searchTerms.next(this.valueText);
    }else {
      this.autoCompleteResults.set([]);
      this.isDropdownOpen.set(false);
    }
  }

  displayProposals(response: any){
    if(response.places != null){
      // console.log(response.places);
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

  blur(event: Event){
    this.isDropdownOpen.set(false);
    this.valueText = "";
    this.selectedDestination.emit(undefined); // On met la valeur à undefined car l'utilisateur n'a pas validé de choix
  }

  chooseDestination(proposal: Destination){
    // Définir la valeur de l'input avec l'élément cliqué
    console.log(proposal);
    this.valueText = proposal.name; // Affuchage du choix à l'utilisateur (update de l'input)
    this.selectedDestination.emit(proposal); // Transmettre la valeur sélectionnée au composant parent
  }

  ngOnDestroy(): void {
    this.searchSub.unsubscribe();
  }
}
