import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TripDestinationsComponent } from './trip-destinations.component';

describe('TripDestinationsComponent', () => {
  let component: TripDestinationsComponent;
  let fixture: ComponentFixture<TripDestinationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TripDestinationsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TripDestinationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
