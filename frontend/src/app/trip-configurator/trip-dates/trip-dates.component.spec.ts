import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TripDatesComponent } from './trip-dates.component';

describe('TripDatesComponent', () => {
  let component: TripDatesComponent;
  let fixture: ComponentFixture<TripDatesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TripDatesComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TripDatesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
