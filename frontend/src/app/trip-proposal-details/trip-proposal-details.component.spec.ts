import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TripProposalDetailsComponent } from './trip-proposal-details.component';
import { Journey } from '../models/journey.model';

describe('TripProposalDetailsComponent', () => {
  let component: TripProposalDetailsComponent;
  let fixture: ComponentFixture<TripProposalDetailsComponent>;

  // Déclaration d'un faux voyage
  const mockJourney : Journey = {
    duration: 28140,
    durations : {
      total: 28140,
      walking: -3960,
    },
    journey_first_section: {
      arrival_date_time: "20250922T095100",
      departure_date_time: "20250922T054900",
      duration: 14520,
      from: {
        embedded_type: "stop_point",
        id: "stop_point:SNCF:87755009:LongDistanceTrain",
        name: "Toulon (Toulon)"
      },
      section_duration: 14520,
      to: {
        embedded_type: "stop_point",
        id: "stop_point:SNCF:87686006:LongDistanceTrain",
        name : "Paris - Gare de Lyon - Hall 1 & 2 (Paris)"
      },
      type: "public_transport",
      transport_type: "",
      display_informations: {
        commercial_mode: "",
        company: "",
        network: "",
        physical_mode: ""
      }
    },
    journey_last_section: {
      type : "public_transport",
      section_duration : 9660,
      duration : 9660,
      from: {
        id : "stop_point:SNCF:87391003:LongDistanceTrain",
        name : "Paris - Montparnasse - Hall 1 & 2 (Paris)",
        embedded_type : "stop_point"
      },
      to: {
        id : "stop_point:SNCF:87476200:LongDistanceTrain",
        name : "Auray (Auray)",
        embedded_type : "stop_point"
      },
      departure_date_time : "20250922T105700",
      arrival_date_time : "20250922T133800",
      transport_type: "",
      display_informations: {
        commercial_mode: "",
        company: "",
        network: "",
        physical_mode: ""
      }
    },
    first_departure_date_time : "2025-09-22T05:49:00",
    nb_transfers : 1,
    sections: [
      {
        from: {
          id : "stop_point:SNCF:87755009:LongDistanceTrain",
          name : "Toulon (Toulon)",
          embedded_type : "stop_point"
        },
          to : {
            id : "stop_point:SNCF:87686006:LongDistanceTrain",
            name : "Paris - Gare de Lyon - Hall 1 & 2 (Paris)",
            embedded_type : "stop_point"
          },
          type : "public_transport",
          section_duration : 14520,
          duration : 14520,
          departure_date_time : "20250922T054900",
          arrival_date_time : "20250922T095100",
          transport_type: "",
          display_informations: {
            commercial_mode: "",
            company: "",
            network: "",
            physical_mode: ""
          }
      },
      {
        from : {
          id : "stop_point:SNCF:87686006:LongDistanceTrain",
          name : "Paris - Gare de Lyon - Hall 1 & 2 (Paris)",
          embedded_type : "stop_point"
        },
        to : {
          id : "stop_point:SNCF:87391003:LongDistanceTrain",
          name : "Paris - Montparnasse - Hall 1 & 2 (Paris)",
          embedded_type : "stop_point"
        },
        type : "Walking",
        section_duration : 3960,
        duration : 3960,
        departure_date_time : "20250922T105700",
        arrival_date_time : "20250922T095100",
        transport_type: "",
        display_informations: {
          commercial_mode: "",
          company: "",
          network: "",
          physical_mode: ""
        }
      },
      {
        from : {
          id : "stop_point:SNCF:87391003:LongDistanceTrain",
          name : "Paris - Montparnasse - Hall 1 & 2 (Paris)",
          embedded_type : "stop_point"
        },
        to: {
          id : "stop_point:SNCF:87476200:LongDistanceTrain",
          name : "Auray (Auray)",
          embedded_type : "stop_point"
        },
        type : "public_transport",
        section_duration : 9660,
        duration : 9660,
        departure_date_time : "20250922T105700",
        arrival_date_time : "20250922T133800",
        transport_type: "",
        display_informations: {
          commercial_mode: "",
          company: "",
          network: "",
          physical_mode: ""
        }
      }
    ]
  }

  beforeEach(async () => {
    Object.defineProperty(window, 'history', {
      value: {
        state: { journey: mockJourney }
      }
    });

    await TestBed.configureTestingModule({
      imports: [TripProposalDetailsComponent]
    })
    .compileComponents();

    // Permanent
    fixture = TestBed.createComponent(TripProposalDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('affiche le titre de la page "Détails du voyage"', () => {
    const compiled = fixture.nativeElement;
    expect(compiled.querySelector('h1')?.textContent).toContain('Détails du voyage');
  });

  // Affichage du voyage bien formaté (pipes)
  // Durée du voyage, nombre de correspondances
  // Heure de départ et lieu de départ
  // durée de la section
  // Heure d'arrivée et lieu d'arrivée
});
