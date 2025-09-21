import { ChangeDetectionStrategy, Component, EventEmitter, output, Output } from '@angular/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MAT_DATE_LOCALE, provideNativeDateAdapter } from '@angular/material/core';
import { FormsModule } from '@angular/forms';
import { MatTimepickerModule } from '@angular/material/timepicker';

@Component({
  selector: 'app-trip-date-picker',
  imports: [
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatTimepickerModule,
    FormsModule
  ],
  templateUrl: './date-picker.component.html',
  styleUrls: ['./date-picker.component.scss'],
  providers: [
    provideNativeDateAdapter(),
    { provide: MAT_DATE_LOCALE, useValue: 'fr-FR' } // Format français
  ],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DatePickerComponent {
  dateChangeEvent = output<Date>();

  readonly today = new Date(new Date().setHours(0, 0, 0, 0)); // minuit
  readonly minDate = this.today;
  readonly maxDate = new Date(this.today.getTime() + 21 * 24 * 60 * 60 * 1000); // +22 jours

  dateSelected: Date = new Date();

  onDateOrTimeChange() {
    this.dateChangeEvent.emit(this.dateSelected);
  }
}
