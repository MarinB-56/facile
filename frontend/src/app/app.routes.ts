import { Routes } from '@angular/router';
import { TripConfiguratorComponent } from './trip-configurator/trip-configurator.component';
import { TripSearchButtonComponent } from './trip-configurator/trip-search-button/trip-search-button.component';
import { TripProposalsListComponent } from './trip-proposals-list/trip-proposals-list.component';
import { TripProposalDetailsComponent } from './trip-proposal-details/trip-proposal-details.component';

export const routes: Routes = [
  {
    path: '', component: TripConfiguratorComponent
  },
  {
    path: 'results', component: TripProposalsListComponent
  },
  {
    path: 'details', component: TripProposalDetailsComponent
  }
];
