import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'destinationPipe'
})
export class DestinationPipePipe implements PipeTransform {

  transform(value: String, ...args: unknown[]): String {

    if(value.includes("Quiberon (Quiberon)")){
      return "Belle-île-en-mer";
    }else{
      const index = value.indexOf('(') - 1;
      return value.substring(0, index);
    }
  }

}
