import { Destination } from "./destination.model";

export interface Section{
  arrival_date_time: String,
  departure_date_time: String,
  display_informations: {
    commercial_mode: String,
    company: String,
    network: String,
    physical_mode: String
  }
  duration: number,
  from: Destination,
  section_duration: number,
  // stop_date_times: [
  //   arrival_date_time: String,
  //   departure_date_time: String,
  //   stop_point: [
  //     id: String,
  //     embedded_type: String,
  //     name: String
  //   ]
  // ],
  to: Destination,
  type: String,
  transport_type: String,
}
