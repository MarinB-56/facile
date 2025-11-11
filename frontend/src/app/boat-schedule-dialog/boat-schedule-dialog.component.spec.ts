import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoatScheduleDialogComponent } from './boat-schedule-dialog.component';

describe('BoatScheduleDialogComponent', () => {
  let component: BoatScheduleDialogComponent;
  let fixture: ComponentFixture<BoatScheduleDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BoatScheduleDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BoatScheduleDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
