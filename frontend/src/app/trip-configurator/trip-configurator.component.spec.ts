import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TripConfiguratorComponent } from './trip-configurator.component';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { Destination } from '../models/destination.model';
import { Trip } from '../models/trip.model';
import { signal } from '@angular/core';

describe('TripConfiguratorComponent', () => {
  let component: TripConfiguratorComponent;
  let fixture: ComponentFixture<TripConfiguratorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TripConfiguratorComponent],
      providers: [
        provideHttpClient(), // Fournit HttpClient
        provideHttpClientTesting(), // Fournit les outils de test pour HttpClient
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TripConfiguratorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // On vérifie que onDepartureSelected va bien changer this.departure
  it('should set "departure" destination when the user selects the departure destination', () => {
    // Création d'une nouvelle destination
    const testDestination : Destination = {name: "Essai", embedded_type: "gare_essai", id: "ESSAI_1"};

    component.onDepartureSelected(testDestination);
    expect(component.departure()).toEqual(testDestination);
  })

  it('should set "arrival" destination when the user selects arrival destination', () => {
    // Création d'une nouvelle destination
    const testDestination : Destination = {name: "Essai", embedded_type: "gare_essai", id: "ESSAI_1"};

    component.onArrivalSelected(testDestination);
    expect(component.arrival()).toEqual(testDestination);
  })

  it('should set the trip date when the user selects a date',()=> {
    const testDate: Date = new Date();

    component.onDateSelected(testDate);
    expect(component.tripDate()).toEqual(testDate);
  })

  it('should swapp destination when asked',() => {
    // On teste onDestinationSwapped()

    // On vérifie que l'arrivée et le départ seront inversées
    const testDeparture: Destination = {name: "Essai departure", embedded_type: "gare_departure", id: "departure_1"};
    const testArrival: Destination = {name: "Essai arrival", embedded_type: "gare_arrival", id: "arrival_1"};

    component.onDepartureSelected(testDeparture);
    component.onArrivalSelected(testArrival);

    component.onDestinationsSwapped();

    expect(component.departure()).toEqual(testArrival);
    expect(component.arrival()).toEqual(testDeparture);
  })

  it('should save the user trip to trip service',() => {
    // Création d'un faux trip
    const testDeparture: Destination = {name: "Essai departure", embedded_type: "gare_departure", id: "departure_1"};
    const testArrival: Destination = {name: "Essai arrival", embedded_type: "gare_arrival", id: "arrival_1"};
    const testDate: Date | null = new Date();

    const testTrip: Trip = {
      departure: testDeparture,
      arrival: testArrival,
      date: testDate
    }

    component.onArrivalSelected(testArrival);
    component.onDepartureSelected(testDeparture);
    component.onDateSelected(testDate);

    component.onSearch();

    expect(component.trip()).toEqual(testTrip);

  })
});
