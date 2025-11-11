import { inject, Injectable } from '@angular/core';
import { Trip } from '../models/trip.model';
import { HttpClient } from '@angular/common/http';
import { Section } from '../models/section.model';

@Injectable({
  providedIn: 'root'
})
export class TripResearchService {
  private results: any;
  private search: any;
  private boatSchedule : any;

  constructor() {

  }

  getBoatSchedule(){
    return this.boatSchedule;
  }

  setBoatSchedule(data: Section[]){
    this.boatSchedule = data;
  }

  setSearch(data: Trip){
    this.search = data;
  }

  getSearch() :Trip {
    return this.search;
  }

  setResults(data: any) {
    this.results = data;
  }

  getResults() {
    return this.results;
  }

  clearResults() {
    this.results = null;
  }
}
