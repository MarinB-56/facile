import { Component, Input } from '@angular/core';
import { fakeAsync } from '@angular/core/testing';

@Component({
  selector: 'app-destination-picker',
  imports: [],
  templateUrl: './destination-picker.component.html',
  styleUrl: './destination-picker.component.css'
})
export class DestinationPickerComponent {
  @Input() placeholderText = "";
  @Input() valueText = "";
  @Input() isReadOnly :boolean = false; // Par défaut, input modifiable
  @Input() isDisabled :boolean = false;

  autoComplete(event :Event){
    const input = event.target as HTMLInputElement;
    const value = input.value;
    console.log(value);
  }
}
