import { Component } from '@angular/core';
import { MatDialogModule } from '@angular/material/dialog';
import { TripProposalSectionComponent } from "../trip-proposal-details/trip-proposal-section/trip-proposal-section.component";
import { Section } from '../models/section.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-boat-schedule-dialog',
  imports: [MatDialogModule, TripProposalSectionComponent, CommonModule],
  templateUrl: './boat-schedule-dialog.component.html',
  styleUrl: './boat-schedule-dialog.component.scss'
})
export class BoatScheduleDialogComponent {
  section:Section = {
      arrival_date_time: "20251129T182000",
      departure_date_time: "20251129T173000",
      duration: 3000,
      from: {
        id:"I56QUI" ,
        name: "Quiberon",
        embedded_type: "harbor"
      },
      section_duration: 3000,
      to: {
        id : "I56BIP",
        name: "Belle-Île-en-Mer - Le Palais",
        embedded_type: "Coucou"
      },
      type:"public_transport",
      transport_type: "essai",
      display_informations: {
        commercial_mode: "String",
        company: "BreizhGo Océane",
        network: "Bangor",
        physical_mode: "Bateau"
      }
  }
}
