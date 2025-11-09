import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TripHeaderComponentComponent } from './trip-header-component.component';

describe('TripHeaderComponentComponent', () => {
  let component: TripHeaderComponentComponent;
  let fixture: ComponentFixture<TripHeaderComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TripHeaderComponentComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TripHeaderComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
