import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DatePickerComponent } from './date-picker.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('DatePickerComponent', () => {
  let component: DatePickerComponent;
  let fixture: ComponentFixture<DatePickerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DatePickerComponent, NoopAnimationsModule],
    }).compileComponents();

    fixture = TestBed.createComponent(DatePickerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call onDateOrTimeChange when user chooses a date', () => {
    // Création d'un espion sur la fonction onDateOrTimeChange
    const spyOnDateOrTimeChange = spyOn(component, 'onDateOrTimeChange');

    // Récupération de l'input de date
    const dateInput: HTMLInputElement = fixture.debugElement.query(By.css('input[matInput]')).nativeElement;

    // Changement de la date et trigger de l'event 'input' (comme si l'utilisateur avait choisi une date)
    dateInput.value = '04/10/2025'; // format fr-FR
    dateInput.dispatchEvent(new Event('input'));
    fixture.detectChanges();

    // Le spy doit avoir vu que onDateOrTimeChange a été appelée
    expect(spyOnDateOrTimeChange).toHaveBeenCalled();
  });

  it('should emit dateChangeEvent when user selects a date', () => {
    spyOn(component.dateChangeEvent, 'emit');

    component.dateSelected = new Date(2025, 9, 4); // 4 octobre 2025
    component.onDateOrTimeChange();

    expect(component.dateChangeEvent.emit).toHaveBeenCalledWith(new Date(2025, 9, 4));
  });

  it('should call onDateOrTimeChange when user chooses a time', () => {
    // Création du spy
    const spyOnDateOrTimeChange = spyOn(component, 'onDateOrTimeChange');

    // On simule la modif du time par l'utilisateur
    // Récupération du champs de l'heure
    const timeInput = fixture.debugElement.query(By.css('input[matInput]')).nativeElement;
    timeInput.value = '15:30';
    timeInput.dispatchEvent(new Event('input'));
    fixture.detectChanges();

    expect(spyOnDateOrTimeChange).toHaveBeenCalled();
  })
});


//
  // it('should emit dateChangeEvent when onDateOrTimeChange is called', () => {
  //   const emitSpy = spyOn(component.dateChangeEvent, 'emit');
  //   component.onDateOrTimeChange();

  //   expect(emitSpy).toHaveBeenCalled();
  // })
