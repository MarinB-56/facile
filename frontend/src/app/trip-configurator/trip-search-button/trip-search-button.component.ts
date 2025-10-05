import { Component, input } from '@angular/core';

@Component({
  selector: 'app-trip-search-button',
  imports: [],
  templateUrl: './trip-search-button.component.html',
  styleUrl: './trip-search-button.component.scss'
})
export class TripSearchButtonComponent {
  // @Input() isDisabled: boolean = true;
  isDisabled = input<boolean>(true);

  onSearch(){
    console.log("On lance la recherche");
  }
}
