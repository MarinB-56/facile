import { Component } from '@angular/core';
import { TripDestinationsComponent } from "./trip-destinations/trip-destinations.component";
import { TripDatesComponent } from "./trip-dates/trip-dates.component";
import { TripSearchButtonComponent } from "./trip-search-button/trip-search-button.component";

@Component({
  selector: 'app-trip-configurator',
  imports: [TripDestinationsComponent, TripDatesComponent, TripSearchButtonComponent],
  templateUrl: './trip-configurator.component.html',
  styleUrl: './trip-configurator.component.css'
})
export class TripConfiguratorComponent {

}
