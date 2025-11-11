import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { Destination } from '../models/destination.model';
import { Router } from '@angular/router';
import { TripResearchService } from '../services/trip-research.service';
import { Journey } from '../models/journey.model';
import { TripProposalItemComponent } from './trip-proposal-item/trip-proposal-item.component';
import { TripHeaderComponentComponent } from '../trip-header-component/trip-header-component.component';
import { DestinationPipePipe } from '../pipes/destination-pipe/destination-pipe.pipe';
import { CommonModule, DatePipe } from '@angular/common';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import { MatDialog } from '@angular/material/dialog';
import { BoatScheduleDialogComponent } from '../boat-schedule-dialog/boat-schedule-dialog.component';
import { Section } from '../models/section.model';
import { TripProposalSectionComponent } from "../trip-proposal-details/trip-proposal-section/trip-proposal-section.component";

@Component({
  selector: 'app-trip-proposals-list',
  imports: [
    TripProposalItemComponent,
    TripHeaderComponentComponent,
    DestinationPipePipe,
    DatePipe,
    MatProgressSpinnerModule,
    CommonModule
],
  templateUrl: './trip-proposals-list.component.html',
  styleUrl: './trip-proposals-list.component.scss'
})
export class TripProposalsListComponent {
  private http = inject(HttpClient);
  private router = inject(Router);
  protected tripResearchService = inject(TripResearchService);

  readonly dialog = inject(MatDialog);

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

      // Récupération des données des voyages
      this.http.post<any>('http://localhost:8080/api/navitia/search', trip, {headers}).subscribe({
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

      // Appel api pour récupérer les données des bateaux
      this.http.post<any>('http://localhost:8080/gtfs/schedule', trip, {headers}).subscribe({
        next: (response) => {
          console.log(response);
          this.tripResearchService.setBoatSchedule(response);
        },
        error: (error) => {
          console.log(error);
        }});
      }

    }

  openBoatSchedule(){
    console.log("Affichage des horaires");

    // Ouverture de la pop up avec les horaires
    const dialogRef = this.dialog.open(BoatScheduleDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });

  }
}
