package org.acme.dto;

import java.sql.Date;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TripDTO {
  private DestinationDTO departure;
  private DestinationDTO arrival;
  private Date date;

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

  public void setDate(Date date){
    this.date = date;
  }

  public Date getDate(){
    return this.date;
  }

  @Override
  public String toString() {
    return "TripDTO{" +
          "departure=" + this.departure.toString() +
          ", arrival=" + this.arrival.toString() +
          ", date=" + this.date +
          '}';
  }
}
