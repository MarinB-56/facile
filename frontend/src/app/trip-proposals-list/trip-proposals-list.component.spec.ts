import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TripProposalsListComponent } from './trip-proposals-list.component';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

describe('TripProposalsListComponent', () => {
  let component: TripProposalsListComponent;
  let fixture: ComponentFixture<TripProposalsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TripProposalsListComponent],
      providers: [
        provideHttpClient(), // Fournit HttpClient
        provideHttpClientTesting(), // Fournit les outils de test pour HttpClient
      ]
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
