import { TestBed } from '@angular/core/testing';

import { TripResearchService } from './trip-research.service';

describe('TripResearchService', () => {
  let service: TripResearchService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TripResearchService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  // On veut que setResults(data) fasse que this.results = data
  // Donc this.results doit être égal à data
  it('setResults should set results successfully', () => {
    // Création data
    const testData = {id: 1, name: 'Test trip'};
    service.setResults(testData);

    expect((service as any).results).toEqual(testData);
  })

  it('getResults should get results data successfully', () => {
    const testData = {id: 1, name: 'Test trip'};

    (service as any).results = testData;

    expect(service.getResults()).toEqual(testData);
  })

  it('clearData should clear results successfully and be equal to null', () => {
    const testData = {id: 1, name: 'Test trip'};
    (service as any).results = testData;
    service.clearResults();

    expect((service as any).results).toEqual(null);
  })
});
