import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TripProposalItemComponent } from './trip-proposal-item.component';

describe('TripProposalItemComponent', () => {
  let component: TripProposalItemComponent;
  let fixture: ComponentFixture<TripProposalItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TripProposalItemComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TripProposalItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
