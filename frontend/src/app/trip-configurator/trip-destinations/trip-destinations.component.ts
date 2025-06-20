import { Component, EventEmitter, Input, Output, signal } from '@angular/core';
import { DatePickerComponent } from "../trip-dates-picker/date-picker.component";
import { DestinationPickerComponent } from "./destination-picker/destination-picker.component";
import { Destination } from '../../models/destination.model';

@Component({
  selector: 'app-trip-destinations',
  imports: [DestinationPickerComponent],
  templateUrl: './trip-destinations.component.html',
  styleUrl: './trip-destinations.component.scss'
})
export class TripDestinationsComponent {
  @Output() selectedDestinationEvent = new EventEmitter<Destination>();
  @Output() swappDestination = new EventEmitter<void>();

  @Input() departure :Destination = {name: "", embedded_type: "", id: ""};
  @Input() arrival :Destination = {name: "", embedded_type: "", id: ""};

  selectedDestination = signal<Destination>({name: "", embedded_type: "", id:""});

  // Récupération de la destination choisie par l'utilisateur
  onDestinationReceivedByPicker(selectedDestination: Destination) {
    this.selectedDestination.set(selectedDestination);
    this.selectedDestinationEvent.emit(this.selectedDestination()); // Envoi de la destination choisie au composant parent
  }

  // Swap button
  swapDestinations() {
    this.swappDestination.emit();
  }
}
