import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DestinationPickerComponent } from './destination-picker.component';
import { provideHttpClient } from '@angular/common/http';
import { provideHttpClientTesting } from '@angular/common/http/testing';

describe('DestinationPickerComponent', () => {
  let component: DestinationPickerComponent;
  let fixture: ComponentFixture<DestinationPickerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DestinationPickerComponent],
      providers: [
        provideHttpClient(), // Fournit HttpClient
        provideHttpClientTesting(), // Fournit les outils de test pour HttpClient
      ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DestinationPickerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
