import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TripConfiguratorHeaderComponent } from './trip-configurator-header.component';

describe('TripConfiguratorHeaderComponent', () => {
  let component: TripConfiguratorHeaderComponent;
  let fixture: ComponentFixture<TripConfiguratorHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TripConfiguratorHeaderComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TripConfiguratorHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
