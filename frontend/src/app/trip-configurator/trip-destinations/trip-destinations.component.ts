import { Component } from '@angular/core';
import { DatePickerComponent } from "../trip-dates/date-picker/date-picker.component";
import { DestinationPickerComponent } from "./destination-picker/destination-picker.component";

@Component({
  selector: 'app-trip-destinations',
  imports: [DatePickerComponent, DestinationPickerComponent],
  templateUrl: './trip-destinations.component.html',
  styleUrl: './trip-destinations.component.css'
})
export class TripDestinationsComponent {

}
