import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TripDestinationsFormComponent } from './trip-destinations-form.component';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { Destination } from '../../models/destination.model';
// import { BELLE_ILE } from '../constants/destination.constants';
import { BELLE_ILE } from '../../constants/destination.constants';

describe('TripDestinationsFormComponent', () => {
  let component: TripDestinationsFormComponent;
  let fixture: ComponentFixture<TripDestinationsFormComponent>;

  const departure: Destination = {name: "Paris", embedded_type: "stop_area", id: "XXX"}
  const arrival: Destination = {name: "Auray (Auray)", embedded_type: "stop_area", id: "stop_area:SNCF:87476200"}

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TripDestinationsFormComponent],
      providers: [
        provideHttpClient(), // Fournit HttpClient
        provideHttpClientTesting(), // Fournit les outils de test pour HttpClient
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TripDestinationsFormComponent);
    component = fixture.componentInstance;

    // Ajout des input required
    // departure = input.required<Destination>();
    // arrival = input.required<Destination>();
    fixture.componentRef.setInput('arrival', arrival);
    fixture.componentRef.setInput('departure', departure);


    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
