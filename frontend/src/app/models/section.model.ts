import { Destination } from "./destination.model";

export interface Section{
  arrival_date_time: String,
  departure_date_time: String,
  duration: number,
  from: Destination,
  section_duration: number,
  to: Destination,
  type: String,
  transport_type: String,
  display_informations: {
    commercial_mode: String,
    company: String,
    network: String,
    physical_mode: String
  }
}
