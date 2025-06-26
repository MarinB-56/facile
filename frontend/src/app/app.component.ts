import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TripConfiguratorComponent } from "./trip-configurator/trip-configurator.component";
import { DestinationPickerComponent } from "./trip-configurator/trip-destinations-form/destination-picker/destination-picker.component";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, TripConfiguratorComponent, DestinationPickerComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'facile';
}
