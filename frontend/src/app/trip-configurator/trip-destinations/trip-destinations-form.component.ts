import { Component, EventEmitter, Input, Output, signal } from '@angular/core';
import { DatePickerComponent } from "../trip-dates-picker/date-picker.component";
import { DestinationPickerComponent } from "./destination-picker/destination-picker.component";
import { Destination } from '../../models/destination.model';

@Component({
  selector: 'app-trip-destinations-form',
  imports: [DestinationPickerComponent],
  templateUrl: './trip-destinations-form.component.html',
  styleUrl: './trip-destinations-form.component.scss'
})
export class TripDestinationsFormComponent {
  @Output() selectedDestinationEvent = new EventEmitter<Destination>();
  @Output() swappDestination = new EventEmitter<void>();

  @Input() departure :Destination = {name: "", embedded_type: "", id: ""};
  @Input() arrival :Destination = {name: "", embedded_type: "", id: ""};

  selectedDestination = signal<Destination>({name: "", embedded_type: "", id:""});
  selectedArrival = signal<Destination>({name: "", embedded_type: "", id:""});

  // Récupération de la destination choisie par l'utilisateur
  onDestinationReceivedByPicker(destination: Destination) {
    // Si c'est la destination arrivée : on met à jour l'arrivée
    console.log()

    // si c'est la destination départ, mise à jour de la destination départ
    this.selectedDestination.set(destination);
    this.selectedDestinationEvent.emit(this.selectedDestination()); // Envoi de la destination choisie au composant parent

    // Sinon, on met à jour l'arrivée

  }

  // Swap button
  swapDestinations() {
    this.swappDestination.emit();
  }
}
