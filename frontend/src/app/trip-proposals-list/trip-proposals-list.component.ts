import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { Destination } from '../models/destination.model';
import { Router } from '@angular/router';
import { TripResearchService } from '../services/trip-research.service';
import { Journey } from '../models/journey.model';
import { TripProposalItemComponent } from './trip-proposal-item/trip-proposal-item.component';
import { TripHeaderComponentComponent } from '../trip-header-component/trip-header-component.component';
import { DestinationPipePipe } from '../pipes/destination-pipe/destination-pipe.pipe';
import { DatePipe } from '@angular/common';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';

@Component({
  selector: 'app-trip-proposals-list',
  imports: [
    TripProposalItemComponent,
    TripHeaderComponentComponent,
    DestinationPipePipe,
    DatePipe,
    MatProgressSpinnerModule
  ],
  templateUrl: './trip-proposals-list.component.html',
  styleUrl: './trip-proposals-list.component.scss'
})
export class TripProposalsListComponent {
  private http = inject(HttpClient);
  private router = inject(Router);
  protected tripResearchService = inject(TripResearchService);

  results : Journey[] = [];

  // pour le spinner de chargement
  isLoading = true;

  ngOnInit(){
    // Récupération du trip envoyé via le service partagé (évite une nouvelle recherche inutile)
    const trip = history.state.trip;

    // On vérifie si le service n'a pas déjà enregistré le résultat d'une recherche
    if(this.tripResearchService.getResults()){
      this.results = this.tripResearchService.getResults();
      this.isLoading = false;

    } else if(trip){ // Appel au serveur pour lancer la recherche d'itinéraires
      const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

      this.http .post<any>('http://localhost:8080/api/navitia/search', trip, {headers}).subscribe({
        next: (response) => {
          // this.tripResearchService.setResults(response);
          console.log(response);
          this.results = response.journeys;
          this.tripResearchService.setResults(this.results);
          this.isLoading = false;
        },
        error: (error) => {
          console.log(error);
          this.isLoading = false;
        }});
    }
  }
}
