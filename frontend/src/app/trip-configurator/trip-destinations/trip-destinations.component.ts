import { Component, EventEmitter, Output, signal } from '@angular/core';
import { DatePickerComponent } from "../trip-dates/date-picker/date-picker.component";
import { DestinationPickerComponent } from "./destination-picker/destination-picker.component";
import { Destination } from '../../models/destination.model';

@Component({
  selector: 'app-trip-destinations',
  imports: [DatePickerComponent, DestinationPickerComponent],
  templateUrl: './trip-destinations.component.html',
  styleUrl: './trip-destinations.component.scss'
})
export class TripDestinationsComponent {
  @Output() selectedDestination = new EventEmitter<Destination>();

  // La ville choisie par l'utilisateur est l'origine du voyage
  isTripOrigin = signal<boolean>(true);

  // Récupération de la destination choisie par l'utilisateur
  onDestinationSelected(destination: Destination) {
    this.selectedDestination.emit(destination); // Envoi de la destination choisie au composant parent
  }

  // Swap button
  swapDestinations() {
    this.isTripOrigin.set(!this.isTripOrigin());
  }
}
