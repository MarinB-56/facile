import { Component, inject, input, signal } from '@angular/core';
import { Journey } from '../../models/journey.model';
import { Router } from '@angular/router';
import { DurationPipePipe } from '../../pipes/duration-pipe/duration-pipe.pipe';
import { TimePipePipe } from '../../pipes/time-pipe/time-pipe.pipe';
import { ConnectionPipePipe } from '../../pipes/connection-pipe/connection-pipe.pipe';

@Component({
  selector: 'app-trip-proposal-item',
  imports: [DurationPipePipe, TimePipePipe, ConnectionPipePipe],
  templateUrl: './trip-proposal-item.component.html',
  styleUrl: './trip-proposal-item.component.scss'
})
export class TripProposalItemComponent {
  router = inject(Router);
  journey = input<Journey>();

  getJourneyDetails(){
    this.router.navigate(['/details'], {
    state: { journey: this.journey() }
  });
  }
}
