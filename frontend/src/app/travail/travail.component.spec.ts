import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TravailComponent } from './travail.component';

describe('TravailComponent', () => {
  let component: TravailComponent;
  let fixture: ComponentFixture<TravailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TravailComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TravailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
