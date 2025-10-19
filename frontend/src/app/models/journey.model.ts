import { Section } from "./section.model"

export interface Journey{
  duration: number,
  durations : {
    total: number,
    walking: number
  },
  journey_first_section: Section,
  journey_last_section: Section,
  first_departure_date_time: String,
  nb_transfers: number,
  sections: Section[]
}
