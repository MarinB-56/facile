import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'durationPipe'
})
export class DurationPipePipe implements PipeTransform {

  transform(duration: number, ...args: unknown[]): string {
    // duration est un nombre en secondes
    let hours = 0;
    let minutes = 0;

    // Comptage des heures
    if(duration >= 3600){
      hours = Math.floor(duration / 3600); // 2h
      duration = duration % 3600 ; // 2460
    }

    // Comptage des minutes
    if(duration >= 60 ){
      minutes = Math.floor( duration / 60);
    }

    return minutes > 0 ? `${hours}h${minutes}` : `${hours}h`;
  }

}
