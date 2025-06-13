import { ChangeDetectionStrategy, Component, computed, EventEmitter, output, Output } from '@angular/core';
import { MatDatepickerInputEvent, MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import {MAT_DATE_LOCALE, provideNativeDateAdapter} from '@angular/material/core';

@Component({
  selector: 'app-trip-date-picker',
  imports: [MatFormFieldModule, MatInputModule, MatDatepickerModule],
  templateUrl: './date-picker.component.html',
  styleUrl: './date-picker.component.scss',
  providers: [
    provideNativeDateAdapter(),
    { provide: MAT_DATE_LOCALE, useValue: 'fr-FR' } // Format de dates à la française
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DatePickerComponent {
  dateSelected = output<Date | null>();

  addEvent(event: MatDatepickerInputEvent<Date>) {
    // console.log(event.value);
    this.dateSelected.emit(event.value);
  }
}
