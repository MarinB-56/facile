import { Component, inject, signal } from '@angular/core';
import { TripDestinationsComponent } from "./trip-destinations/trip-destinations.component";
import { TripDatesComponent } from "./trip-dates/trip-dates.component";
import { TripSearchButtonComponent } from "./trip-search-button/trip-search-button.component";
import { signalSetFn } from '@angular/core/primitives/signals';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Destination } from '../models/destination.model';
import { Trip } from '../models/trip.model';
import { BELLE_ILE } from '../constants/destination.constants';

@Component({
  selector: 'app-trip-configurator',
  imports: [TripDestinationsComponent, TripDatesComponent, TripSearchButtonComponent],
  templateUrl: './trip-configurator.component.html',
  styleUrl: './trip-configurator.component.css'
})
export class TripConfiguratorComponent {
  private http = inject(HttpClient);
  readonly urlAutoComplete = "http://localhost:8080/api/navitia/search";

  destination1 = signal<Destination>({name: "", embedded_type: "", id: ""});
  destination2 = signal<Destination>(BELLE_ILE); // Par défaut, la destination est Belle-ile

  isButtonDisabled = signal(true);
  trip = signal<Trip>({
    departure: this.destination1(),
    arrival: BELLE_ILE, //Constante pour le moment
    date: null
  });

  // Récupération de la valeur choisie par l'utilisateur (composant petit enfant)
  onDestinationSelected(destination: Destination){

    // Si une destination a été choisie, on active le bouton
    if(destination !== undefined){
      this.destination1.set(destination);
      this.isButtonDisabled.set(false);
      console.log(destination);
    } else {
      this.destination1.set({name: "", embedded_type: "", id: ""});
      this.isButtonDisabled.set(true);
    }
  }

  onSearch(){
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

    //Envoi des données au backend
    console.log(this.destination1())
    this.http
      .post<Destination>(`${this.urlAutoComplete}`, this.destination1(), {headers})
      .subscribe(response => {
        console.log(response);
      });
  }
}
