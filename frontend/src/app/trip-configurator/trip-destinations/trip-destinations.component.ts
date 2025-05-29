import { Component, EventEmitter, Output } from '@angular/core';
import { DatePickerComponent } from "../trip-dates/date-picker/date-picker.component";
import { DestinationPickerComponent } from "./destination-picker/destination-picker.component";

@Component({
  selector: 'app-trip-destinations',
  imports: [DatePickerComponent, DestinationPickerComponent],
  templateUrl: './trip-destinations.component.html',
  styleUrl: './trip-destinations.component.css'
})
export class TripDestinationsComponent {
  @Output() selectedDestination = new EventEmitter<{ name: string, embedded_type: string }>();

  // Récupération de la destination choisie par l'utilisateur
  onDestinationSelected(destination: { name: string, embedded_type: string }) {
    this.selectedDestination.emit(destination); // Envoi de la destination choisie au composant parent
  }
}
