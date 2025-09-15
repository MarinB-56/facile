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

    // 1h0 => 1h
    // 0h42 => 42 min
    // 2h34 => 2h34
    if(hours === 0){
      return `${minutes} min`;
    }else if(minutes > 0){
      return `${hours}h${minutes}`
    }else {
      return `${hours}h`;
    }

  }

}
