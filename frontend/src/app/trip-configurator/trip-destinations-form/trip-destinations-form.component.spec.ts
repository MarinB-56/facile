import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TripDestinationsFormComponent } from './trip-destinations-form.component';

describe('TripDestinationsComponent', () => {
  let component: TripDestinationsFormComponent;
  let fixture: ComponentFixture<TripDestinationsFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TripDestinationsFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TripDestinationsFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
