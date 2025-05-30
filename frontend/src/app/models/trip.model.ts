import { Destination } from "./destination.model";

export interface Trip {
  departure: Destination;
  arrival: Destination;
  date: Date | null;
}
