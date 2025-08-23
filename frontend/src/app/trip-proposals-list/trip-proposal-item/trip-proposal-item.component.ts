import { Component, input, signal } from '@angular/core';
import { Journey } from '../../models/journey.model';

@Component({
  selector: 'app-trip-proposal-item',
  imports: [],
  templateUrl: './trip-proposal-item.component.html',
  styleUrl: './trip-proposal-item.component.scss'
})
export class TripProposalItemComponent {
  journey = input<Journey>();
}
