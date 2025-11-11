import { Component, inject } from '@angular/core';
import { MatDialogModule } from '@angular/material/dialog';
import { TripProposalSectionComponent } from "../trip-proposal-details/trip-proposal-section/trip-proposal-section.component";
import { Section } from '../models/section.model';
import { CommonModule } from '@angular/common';
import { TripResearchService } from '../services/trip-research.service';

@Component({
  selector: 'app-boat-schedule-dialog',
  imports: [MatDialogModule, TripProposalSectionComponent, CommonModule],
  templateUrl: './boat-schedule-dialog.component.html',
  styleUrl: './boat-schedule-dialog.component.scss'
})
export class BoatScheduleDialogComponent {
  sections : Section[] | undefined;

  tripResearchService = inject(TripResearchService);

  ngOnInit(){
    this.sections = this.tripResearchService.getBoatSchedule();
  }
}
