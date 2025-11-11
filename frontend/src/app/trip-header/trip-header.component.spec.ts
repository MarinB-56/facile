import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TripHeaderComponent } from './trip-header.component';

describe('TripHeaderComponentComponent', () => {
  let component: TripHeaderComponent;
  let fixture: ComponentFixture<TripHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TripHeaderComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TripHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
