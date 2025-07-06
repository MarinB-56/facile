package org.acme.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TripDTO {
  private StopPointDTO departure;
  private StopPointDTO arrival;
  private LocalDateTime date;

  public TripDTO() {}

  public void setDeparture(StopPointDTO departure){
    this.departure = departure;
  }

  public StopPointDTO getDeparture(){
    return this.departure;
  }

  public void setArrival(StopPointDTO arrival){
    this.arrival = arrival;
  }

  public StopPointDTO getArrival(){
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
