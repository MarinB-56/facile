import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TripProposalDetailsComponent } from './trip-proposal-details.component';

describe('TripProposalDetailsComponent', () => {
  let component: TripProposalDetailsComponent;
  let fixture: ComponentFixture<TripProposalDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TripProposalDetailsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TripProposalDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
