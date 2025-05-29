import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-trip-search-button',
  imports: [],
  templateUrl: './trip-search-button.component.html',
  styleUrl: './trip-search-button.component.css'
})
export class TripSearchButtonComponent {
  @Input() isDisabled: boolean = true;

  onSearch(){
    console.log("On lance la recherche");
  }
}
