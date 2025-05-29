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

  onDestinationSelected(destination: { name: string, embedded_type: string }) {
    console.log(destination);
    this.selectedDestination.emit(destination);
    console.log(`Destination envoyée au parent :${destination}`);
  }
}
