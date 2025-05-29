import { Component, signal } from '@angular/core';
import { TripDestinationsComponent } from "./trip-destinations/trip-destinations.component";
import { TripDatesComponent } from "./trip-dates/trip-dates.component";
import { TripSearchButtonComponent } from "./trip-search-button/trip-search-button.component";
import { signalSetFn } from '@angular/core/primitives/signals';

@Component({
  selector: 'app-trip-configurator',
  imports: [TripDestinationsComponent, TripDatesComponent, TripSearchButtonComponent],
  templateUrl: './trip-configurator.component.html',
  styleUrl: './trip-configurator.component.css'
})
export class TripConfiguratorComponent {
  selectedDestination = signal({});
  isButtonDisabled = signal(true);

  // Récupération de la valeur choisie par l'utilisateur (composant petit enfant)
  onDestinationSelected(destination: {name: String, embedded_type: string}){
    this.selectedDestination.set(destination.name);
    this.isButtonDisabled.set(!this.selectedDestination);
  }
}
