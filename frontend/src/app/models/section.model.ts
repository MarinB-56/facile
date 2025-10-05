import { Destination } from "./destination.model";

export interface Section{
  arrival_date_time: String,
  departure_date_time: String,
  duration: number,
  from: Destination,
  sectionDuration: number,
  to: Destination,
  type: String,
  transport_type: String
}
