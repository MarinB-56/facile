import { Component, EventEmitter, input, output, signal } from '@angular/core';
import { DatePickerComponent } from "../trip-dates-picker/date-picker.component";
import { DestinationPickerComponent } from "./destination-picker/destination-picker.component";
import { Destination } from '../../models/destination.model';
import { BELLE_ILE } from '../../constants/destination.constants';

@Component({
  selector: 'app-trip-destinations-form',
  imports: [DestinationPickerComponent],
  templateUrl: './trip-destinations-form.component.html',
  styleUrl: './trip-destinations-form.component.scss'
})
export class TripDestinationsFormComponent {
  // Fournit par le composant parent
  departure = input.required<Destination>();
  arrival = input.required<Destination>();

  // Envoi au composant parent
  departureSelectedEvent = output<Destination>();
  arrivalSelectedEvent = output<Destination>();
  swappDestinationEvent = output();

  // Transmission de la destination de Départ
  onDepartureReceivedByPicker(destination: Destination) {
    this.departureSelectedEvent.emit(destination);
  }

  // Transmission de la destination d'Arrivée
  onArrivalReceivedByPicker(destination: Destination) {
    this.arrivalSelectedEvent.emit(destination);
  }

  // Swap button
  swapDestinations() {
    console.log("Emit swappDestinationEvent");
    this.swappDestinationEvent.emit(); // Prévient le composant parent
  }
}


  // @Input() departure!: Destination ;
  // @Input() arrival!: Destination;

  // @Output() departureSelectedEvent = new EventEmitter<Destination>();
  // @Output() arrivalSelectedEvent = new EventEmitter<Destination>();
  // @Output() swappDestinationEvent = new EventEmitter<void>();
