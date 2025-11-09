import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'destinationPipe'
})
export class DestinationPipePipe implements PipeTransform {

  transform(value: String, ...args: unknown[]): String {


    return "Coucou";
  }

}
