import { Component, inject, input, signal } from '@angular/core';
import { Journey } from '../../models/journey.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-trip-proposal-item',
  imports: [],
  templateUrl: './trip-proposal-item.component.html',
  styleUrl: './trip-proposal-item.component.scss'
})
export class TripProposalItemComponent {
  router = inject(Router);

  journey = input<Journey>();

  getJourneyDetails(){

    this.router.navigate(['/details']);
  }
}
