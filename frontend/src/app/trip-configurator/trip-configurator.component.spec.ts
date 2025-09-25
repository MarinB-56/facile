import { ComponentFixture, TestBed } from '@angular/core/testing';
import { TripConfiguratorComponent } from './trip-configurator.component';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

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
});
