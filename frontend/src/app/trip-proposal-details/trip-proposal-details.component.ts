import { Component } from '@angular/core';
import { Journey } from '../models/journey.model';
import { DurationPipePipe } from '../pipes/duration-pipe/duration-pipe.pipe';
import { ConnectionPipePipe } from '../pipes/connection-pipe/connection-pipe.pipe';
import { TimePipePipe } from '../pipes/time-pipe/time-pipe.pipe';

@Component({
  selector: 'app-trip-proposal-details',
  imports: [DurationPipePipe, ConnectionPipePipe, TimePipePipe],
  templateUrl: './trip-proposal-details.component.html',
  styleUrl: './trip-proposal-details.component.scss'
})
export class TripProposalDetailsComponent {
  journey: Journey | undefined;

  ngOnInit(){
    this.journey = history.state.journey;
    console.log(this.journey);
  }
}
