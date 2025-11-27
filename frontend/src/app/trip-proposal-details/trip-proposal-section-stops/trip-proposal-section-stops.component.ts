import { Component, input, output, Signal, signal, WritableSignal } from '@angular/core';
import { MatAccordion, MatExpansionPanel, MatExpansionModule } from "@angular/material/expansion";
import { TimePipePipe } from "../../pipes/time-pipe/time-pipe.pipe";

@Component({
  selector: 'app-trip-proposal-section-stops',
  imports: [MatAccordion, MatExpansionPanel, MatExpansionModule, TimePipePipe],
  templateUrl: './trip-proposal-section-stops.component.html',
  styleUrl: './trip-proposal-section-stops.component.scss'
})
export class TripProposalSectionStopsComponent {
  section = input<any>();

  icon = input<string>();
  title = input<string>();
  
  panelOpenState = input();
  panelOpenChange = output<boolean>();
}

// icon = input.required<string>();
// title = input.required<string>();
