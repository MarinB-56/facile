import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { Destination } from '../models/destination.model';
import { Router } from '@angular/router';
import { TripResearchService } from '../services/trip-research.service';
import { Journey } from '../models/journey.model';

@Component({
  selector: 'app-trip-proposals-list',
  imports: [],
  templateUrl: './trip-proposals-list.component.html',
  styleUrl: './trip-proposals-list.component.scss'
})
export class TripProposalsListComponent {
  private http = inject(HttpClient);
  private router = inject(Router);
  private tripResearchService = inject(TripResearchService);

  results : Journey[] = [];

  ngOnInit(){
    // Récupération du trip envoyé via le service partagé
    const trip = this.tripResearchService.getResults();

    console.log(trip);

    // Appel au serveur pour lancer la recherche d'itinéraires
    if(trip){
      const headers = new HttpHeaders({ 'Content-Type': 'application/json' });

      this.http .post<any>('http://localhost:8080/api/navitia/search', trip, {headers})
        .subscribe(response => {
          // this.tripResearchService.setResults(response);
          console.log(response);
          this.results = response.journeys;
      });
    }
  }
}
