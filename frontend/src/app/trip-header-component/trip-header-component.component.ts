import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-trip-header-component',
  imports: [],
  templateUrl: './trip-header-component.component.html',
  styleUrl: './trip-header-component.component.scss'
})
export class TripHeaderComponentComponent {
  private router = inject(Router);

  handleTitleClick(){
    this.router.navigate(['/']);
  }
}
