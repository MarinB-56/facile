import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TripConfiguratorComponent } from './trip-configurator.component';

describe('TripConfiguratorComponent', () => {
  let component: TripConfiguratorComponent;
  let fixture: ComponentFixture<TripConfiguratorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TripConfiguratorComponent]
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
