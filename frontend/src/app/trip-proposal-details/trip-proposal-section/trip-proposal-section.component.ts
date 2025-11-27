import { Component, effect, input, signal, ViewChild } from '@angular/core';
import { TimePipePipe } from "../../pipes/time-pipe/time-pipe.pipe";
import { DurationPipePipe } from "../../pipes/duration-pipe/duration-pipe.pipe";
import { TransportTypePipePipe } from '../../pipes/transport-type-pipe/transport-type-pipe.pipe';
import { CommonModule } from '@angular/common';
import { MatExpansionModule, MatExpansionPanel } from '@angular/material/expansion';
import { TripProposalSectionStopsComponent } from "../trip-proposal-section-stops/trip-proposal-section-stops.component";

@Component({
  selector: 'app-trip-proposal-section',
  standalone: true,
  imports: [TimePipePipe, DurationPipePipe, TransportTypePipePipe, CommonModule, MatExpansionModule, TripProposalSectionStopsComponent],
  templateUrl: './trip-proposal-section.component.html',
  styleUrl: './trip-proposal-section.component.scss'
})
export class TripProposalSectionComponent {
  section = input.required<any>();
  readonly panelOpenState = signal(false);

  getIcon() : string {
    if( this.section().display_informations.physical_mode.includes("Train") || this.section().display_informations.physical_mode.includes("TER") ) {
      return "directions_railway_2";
    }else if(this.section().display_informations.physical_mode.includes("Autocar")){
      return "directions_bus"
    }else if(this.section().display_informations.physical_mode.includes("Bateau")){
      return "directions_boat";
    }

    return "question_mark";
  }

  getTransportTitle(): string {
    if (this.section().display_informations.physical_mode.includes("Train")) {
      return this.section().display_informations.network;
    } else if (this.section().display_informations.physical_mode.includes("TER")) {
      return this.section().display_informations.physical_mode;
    } else if (this.section().display_informations.physical_mode.includes("Bateau")) {
      return `${this.section().display_informations.physical_mode} - ${this.section().display_informations.network}`;
    }
    return this.section().display_informations.physical_mode;
  }
}
