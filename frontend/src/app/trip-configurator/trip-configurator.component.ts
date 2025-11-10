import { Component, computed, effect, inject, signal } from '@angular/core';
import { TripDestinationsFormComponent } from "./trip-destinations-form/trip-destinations-form.component";
import { TripSearchButtonComponent } from "./trip-search-button/trip-search-button.component";
import { Destination } from '../models/destination.model';
import { BELLE_ILE } from '../constants/destination.constants';
import { Router } from '@angular/router';

import { DatePickerComponent } from "./trip-dates-picker/date-picker.component";
import { TripResearchService } from '../services/trip-research.service';
import { TripConfiguratorHeaderComponent } from "./trip-configurator-header/trip-configurator-header.component";
import { TripHeaderComponentComponent } from "../trip-header-component/trip-header-component.component";


@Component({
  selector: 'app-trip-configurator',
  imports: [TripDestinationsFormComponent, TripSearchButtonComponent, DatePickerComponent, TripConfiguratorHeaderComponent, TripHeaderComponentComponent],
  templateUrl: './trip-configurator.component.html',
  styleUrl: './trip-configurator.component.scss'
})
export class TripConfiguratorComponent {
  private router = inject(Router);
  private tripResearchService = inject(TripResearchService);

  readonly urlAutoComplete = "http://localhost:8080/api/navitia/search";

  // Le bouton de recherche est désactivé par défaut
  isButtonDisabled = signal(true);

  // Initialisation des destinations et dates
  departure = signal<Destination>({name: "", embedded_type: "", id: ""});
  // arrival = signal<Destination>({name: "", embedded_type: "", id: ""});
  arrival = signal<Destination>(BELLE_ILE); // Par défaut, la destination est Belle-ile

  tripDate = signal<Date | null>(null);

  constructor(){
    effect(() => {
      const search = this.tripResearchService.getSearch();

      if(search){
        this.departure.set(search.departure);
        this.arrival.set(search.arrival);
      }
    })
  }

  // Voyage complet. Mis à jour si ses champs sont mis à jour (grâce à computed)
  trip = computed(() => ({
    departure: this.departure(),
    arrival: this.arrival(), //Constante pour le moment
    date: this.tripDate()
  }));

  // Vérification que le voyage est complet pour activation ou non du bouton de recherche
  isTripValid = computed(() => {
    const departure = this.departure();
    const arrival = this.arrival();
    const date = this.tripDate();

    console.log("Vérification du bouton");

    return departure.id !== "" && arrival.id !== "" && date !== null;
  });

  // Récupération de la destination de départ
  onDepartureSelected(destination: Destination){
    this.departure.set(destination);
    console.log(this.trip());
  }

  // Récupération de la destination d'arrivée
  onArrivalSelected(destination: Destination){
    this.arrival.set(destination);
    console.log(this.trip());
  }

  // Récupération de la date choisie
  onDateSelected(date :Date | null){
    this.tripDate.set(date);
    console.log(this.trip());
  }

  // Inversion des destinations (inversion aller et retour)
  onDestinationsSwapped(event: any){
    this.departure.set(event.departure);
    this.arrival.set(event.arrival);

    console.log("Destinations have been swapped");
    console.log(this.trip());
  }

  // Lancement de la recherche ; envoi des données au backend
  onSearch(){
    // On supprime les anciennes recherches
    this.tripResearchService.clearResults();

    // On met à jour la recherche
    this.tripResearchService.setSearch(this.trip());

    // Navigation vers la page des résultats
    this.router.navigate(['/trips'], {
      state: { trip: this.trip() }
    });
  }
}
