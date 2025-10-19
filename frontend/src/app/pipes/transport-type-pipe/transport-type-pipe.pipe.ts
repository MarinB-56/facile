import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'transportTypePipe'
})
export class TransportTypePipePipe implements PipeTransform {

  transform(value: String, ...args: unknown[]): String {
    // Récupération de la valeur en tant que String
    if(value.includes("Train") || value.includes("TER") || value.includes("Intercités")){
      return "train";
    }else if(value.includes("Bateau")){
      return "boat";
    }else if(value.includes("Autocar")){
      return "bus";
    }

    return "Unknown";
  }


}
