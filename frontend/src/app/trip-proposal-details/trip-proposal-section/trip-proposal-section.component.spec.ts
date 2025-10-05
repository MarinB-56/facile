import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TripProposalSectionComponent } from './trip-proposal-section.component';

describe('TripProposalSectionComponent', () => {
  let component: TripProposalSectionComponent;
  let fixture: ComponentFixture<TripProposalSectionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TripProposalSectionComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TripProposalSectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
