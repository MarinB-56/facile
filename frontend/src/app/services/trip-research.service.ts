import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TripResearchService {
  private results: any;

  constructor() {

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
