import { Component, signal } from '@angular/core';
import { Section } from '../models/section.model';
import { TripProposalSectionComponent } from "../trip-proposal-details/trip-proposal-section/trip-proposal-section.component";
import {MatExpansionModule} from '@angular/material/expansion';

@Component({
  selector: 'app-travail',
  imports: [TripProposalSectionComponent, MatExpansionModule],
  templateUrl: './travail.component.html',
  styleUrl: './travail.component.scss'
})
export class TravailComponent {
  section = {
    arrival_date_time: "20251117T205700",
    departure_date_time: "20251117T173700",
    duration: 12000,
    from: {
      id:"stop_point:SNCF:87686006:LongDistanceTrain" ,
      name: "Paris - Gare de Lyon - Hall 1 & 2 (Paris)",
      embedded_type: "stop_point"
    },
    section_duration: 12000,
    to: {
      id : "stop_point:SNCF:87751008:LongDistanceTrain",
      name: "Marseille Saint-Charles (Marseille)",
      embedded_type: "stop_point"
    },
    type:"public_transport",
    transport_type: "essai",
    display_informations: {
      commercial_mode: "TGV INOUI",
      company: "SNCF Voyageurs",
      network: "TGV INOUI",
      physical_mode: "Train grande vitesse"
    },
    stop_date_times: [
      {
        arrival_date_time: "20251117T173700",
        departure_date_time: "20251117T173700",
        stop_point: {
            id: "stop_point:SNCF:87686006:LongDistanceTrain",
            name: "Paris - Gare de Lyon - Hall 1 & 2 (Paris)"
        }
      },
      {
        arrival_date_time: "20251117T201700",
        departure_date_time: "20251117T202000",
        stop_point: {
            id: "stop_point:SNCF:87318964:LongDistanceTrain",
            name: "Avignon TGV (Avignon)"
        }
      },
      {
        arrival_date_time: "20251117T204100",
        departure_date_time: "20251117T204400",
        stop_point: {
            id: "stop_point:SNCF:87319012:LongDistanceTrain",
            name: "Aix-en-Provence TGV"
        }
      },
      {
        arrival_date_time: "20251117T205700",
        departure_date_time: "20251117T205700",
        stop_point: {
            id: "stop_point:SNCF:87751008:LongDistanceTrain",
            name: "Marseille Saint-Charles"
        }
      }
    ]
  }

  readonly panelOpenState = signal(false);
}
