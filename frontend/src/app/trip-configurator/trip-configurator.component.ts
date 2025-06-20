import { Component, computed, inject, signal } from '@angular/core';
import { TripDestinationsComponent } from "./trip-destinations/trip-destinations.component";
import { TripSearchButtonComponent } from "./trip-search-button/trip-search-button.component";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Destination } from '../models/destination.model';
import { BELLE_ILE } from '../constants/destination.constants';

import {MatDatepickerModule} from '@angular/material/datepicker';
import { DatePickerComponent } from "./trip-dates-picker/date-picker.component";

@Component({
  selector: 'app-trip-configurator',
  imports: [TripDestinationsComponent, TripSearchButtonComponent, DatePickerComponent],
  templateUrl: './trip-configurator.component.html',
  styleUrl: './trip-configurator.component.scss'
})
export class TripConfiguratorComponent {
  private http = inject(HttpClient);
  readonly urlAutoComplete = "http://localhost:8080/api/navitia/search";

  // Le bouton de recherche est désactivé par défaut
  isButtonDisabled = signal(true);

  // Initialisation des destinations et dates
  destination1 = signal<Destination>({name: "", embedded_type: "", id: ""});
  destination2 = signal<Destination>(BELLE_ILE); // Par défaut, la destination est Belle-ile
  tripDate = signal<Date | null>(null);

  // Voyage complet. Mis à jour si ses champs sont mis à jour (grâce à computed)
  trip = computed(() => ({
    departure: this.destination1(),
    arrival: this.destination2(), //Constante pour le moment
    date: this.tripDate()
  }));

  // Vérification que le voyage est complet pour activation ou non du bouton de recherche
  isTripValid = computed(() => {
    const departure = this.destination1();
    const arrival = this.destination2();
    const date = this.tripDate();

    console.log("Vérification du bouton");

    return departure.id !== "" && arrival.id !== "" && date !== null;
  });

  // Récupération de la destination choisie
  onDestinationSelected(destination: Destination){
    if(destination !== undefined){
      this.destination1.set(destination); // On met à jour la destination choisie
    } else {
      this.destination1.set({name: "", embedded_type: "", id: ""}); // On met une valeur par défaut dans l'input
    }
    console.log(this.trip());
  }

  // Récupération de la date choisie
  onDateSelected(date :Date | null){
    if(date !== undefined){
      this.tripDate.set(date);
    }
    console.log(this.trip());
  }

  // Inversion des destinations
  onDestinationsSwapped(){
    // Inversion de l'arrivée et du départ niveau trip
    const tampon: Destination = this.destination1();

    this.destination1.set(this.destination2());
    this.destination2.set(tampon);
    console.log(this.trip());
  }

  // Lancement de la recherche ; envoi des données au backend
  onSearch(){
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    //Envoi des données au backend
    console.log(this.trip())
    this.http
      .post<Destination>(`${this.urlAutoComplete}`, this.destination1(), {headers})
      .subscribe(response => {
        console.log(response);
      });
  }
}
