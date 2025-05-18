import { Component } from '@angular/core';
import { DatePickerComponent } from "./date-picker/date-picker.component";

@Component({
  selector: 'app-trip-dates',
  imports: [DatePickerComponent],
  templateUrl: './trip-dates.component.html',
  styleUrl: './trip-dates.component.css'
})
export class TripDatesComponent {

}
