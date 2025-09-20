import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'connectionPipe'
})
export class ConnectionPipePipe implements PipeTransform {

  transform(value: number, ...args: unknown[]): string {
    if(value === 0){
      return "Direct";
    } else if(value === 1){
      return "1 correspondance";
    } else if(value > 1){
      return `${value} correspondances`;
    } else {
      return "NaN";
    }
  }
}
