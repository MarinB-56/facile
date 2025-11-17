import { Component, input, signal } from '@angular/core';
import { Trip } from '../../models/trip.model';
import { Section } from '../../models/section.model';
import { TimePipePipe } from "../../pipes/time-pipe/time-pipe.pipe";
import { DurationPipePipe } from "../../pipes/duration-pipe/duration-pipe.pipe";
import { TransportTypePipePipe } from '../../pipes/transport-type-pipe/transport-type-pipe.pipe';
import { CommonModule } from '@angular/common';
import { MatExpansionModule } from '@angular/material/expansion';

@Component({
  selector: 'app-trip-proposal-section',
  standalone: true,
  imports: [TimePipePipe, DurationPipePipe, TransportTypePipePipe, CommonModule, MatExpansionModule],
  templateUrl: './trip-proposal-section.component.html',
  styleUrl: './trip-proposal-section.component.scss'
})
export class TripProposalSectionComponent {
  section = input.required<any>();

  readonly panelOpenState = signal(false);
}
