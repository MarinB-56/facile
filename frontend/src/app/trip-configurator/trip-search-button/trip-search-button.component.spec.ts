import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TripSearchButtonComponent } from './trip-search-button.component';

describe('TripSearchButtonComponent', () => {
  let component: TripSearchButtonComponent;
  let fixture: ComponentFixture<TripSearchButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TripSearchButtonComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TripSearchButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
