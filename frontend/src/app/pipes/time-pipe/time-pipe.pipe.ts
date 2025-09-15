import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'timePipe'
})
export class TimePipePipe implements PipeTransform {

  transform(value: any, ...args: unknown[]): string {
    // On récupère value sous forme de 20250920T065300
    let year = parseInt(value.slice(0,4));
    let month = parseInt(value.slice(4,6));
    let day = parseInt(value.slice(6,8));
    let hour = parseInt(value.slice(9,11));
    let minutes = parseInt(value.slice(11,13));
    let seconds = parseInt(value.slice(13,15));

    let date = new Date(year, month, day, hour, minutes, seconds);

    // Ajoute des 0 si les heures ou les minutes sont inférieurs à 10
    const formatedHour = date.getHours().toString().padStart(2,'0');
    const formatedMinutes = date.getMinutes().toString().padStart(2, '0');

    return `${formatedHour}h${formatedMinutes}`;
  }

}
