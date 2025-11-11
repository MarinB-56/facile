import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-trip-header',
  imports: [],
  templateUrl: './trip-header.component.html',
  styleUrl: './trip-header.component.scss'
})
export class TripHeaderComponent {
  private router = inject(Router);

  handleTitleClick(){
    this.router.navigate(['/']);
  }
}
