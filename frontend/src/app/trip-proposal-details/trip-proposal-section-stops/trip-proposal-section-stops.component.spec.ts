import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TripProposalSectionStopsComponent } from './trip-proposal-section-stops.component';

describe('TripProposalSectionStopsComponent', () => {
  let component: TripProposalSectionStopsComponent;
  let fixture: ComponentFixture<TripProposalSectionStopsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TripProposalSectionStopsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TripProposalSectionStopsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
