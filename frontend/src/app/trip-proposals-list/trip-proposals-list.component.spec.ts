import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TripProposalsListComponent } from './trip-proposals-list.component';

describe('TripProposalsListComponent', () => {
  let component: TripProposalsListComponent;
  let fixture: ComponentFixture<TripProposalsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TripProposalsListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TripProposalsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
