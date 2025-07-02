package org.acme.dto;

import java.sql.Date;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TripDTO {
  private DestinationDTO departure;
  private DestinationDTO arrival;
  private LocalDateTime date;

  public TripDTO() {}

  public void setDeparture(DestinationDTO departure){
    this.departure = departure;
  }

  public DestinationDTO getDeparture(){
    return this.departure;
  }

  public void setArrival(DestinationDTO arrival){
    this.arrival = arrival;
  }

  public DestinationDTO getArrival(){
    return this.arrival;
  }

  public void setDate(LocalDateTime date){
    this.date = date;
  }

  public LocalDateTime getDate(){
    return this.date;
  }

  @Override
  public String toString() {
    return "TripDTO:\n " + "- Departure = " + this.departure.toString() + ",\n - Arrival = " + this.arrival.toString() + ",\n - Date = " + this.date;
  }
}
