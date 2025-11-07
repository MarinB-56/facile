import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TripProposalsBoatsModalComponent } from './trip-proposals-boats-modal.component';

describe('TripProposalsBoatsModalComponent', () => {
  let component: TripProposalsBoatsModalComponent;
  let fixture: ComponentFixture<TripProposalsBoatsModalComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TripProposalsBoatsModalComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TripProposalsBoatsModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
