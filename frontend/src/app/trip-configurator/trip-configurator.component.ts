import { Component, computed, inject, signal } from '@angular/core';
import { TripDestinationsComponent } from "./trip-destinations/trip-destinations.component";
import { TripDatesComponent } from "./trip-dates/trip-dates.component";
import { TripSearchButtonComponent } from "./trip-search-button/trip-search-button.component";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Destination } from '../models/destination.model';
import { BELLE_ILE } from '../constants/destination.constants';

@Component({
  selector: 'app-trip-configurator',
  imports: [TripDestinationsComponent, TripSearchButtonComponent],
  templateUrl: './trip-configurator.component.html',
  styleUrl: './trip-configurator.component.scss'
})
export class TripConfiguratorComponent {
  private http = inject(HttpClient);
  readonly urlAutoComplete = "http://localhost:8080/api/navitia/search";
  isButtonDisabled = signal(true);

  destination1 = signal<Destination>({name: "", embedded_type: "", id: ""});
  destination2 = signal<Destination>(BELLE_ILE); // Par défaut, la destination est Belle-ile
  tripDate = signal<Date | null>(null);

  // Voyage complet. Mis à jour si ses champs sont mis à jour (grâce à computed)
  trip = computed(() => ({
    departure: this.destination1(),
    arrival: BELLE_ILE, //Constante pour le moment
    date: this.tripDate()
  }));

  // Récupération de la valeur choisie par l'utilisateur (composant petit enfant)
  onDestinationSelected(destination: Destination){
    if(destination !== undefined){
      this.destination1.set(destination); // On met à jour la destination choisie
      this.isButtonDisabled.set(false);     // On active le bouton recherche
    } else {
      this.destination1.set({name: "", embedded_type: "", id: ""}); // On met une valeur par défaut dans l'input
      this.isButtonDisabled.set(true); // on désactive le bouton recherche
    }
  }

  // Lancement de la recherche. Envoi des données au backend
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
