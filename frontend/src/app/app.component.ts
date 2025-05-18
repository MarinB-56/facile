import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TripConfiguratorComponent } from "./trip-configurator/trip-configurator.component";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, TripConfiguratorComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'facile';
}
