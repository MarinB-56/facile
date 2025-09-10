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
});
