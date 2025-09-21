import { Section } from "./section.model"

export interface Journey{
  duration: number,
  durations : {
    total: number,
    walking: number
  },
  journeyFirstSection: Section,
  journeyLastSection: Section,
  firstDeparturDateTime: String,
  nb_transfers: number,
  sections: Section[]
}
