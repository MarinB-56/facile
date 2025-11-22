package org.acme.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StopDateTimeDTO {
  @JsonProperty("arrival_date_time")
  private String arrivalDateTime;

  @JsonProperty("")
  private String departureDateTime;

  @JsonProperty("stop_point")
  private StopPointDTO stopPoint;

  public String getArrivalDateTime() {
    return arrivalDateTime;
  }

  public void setArrivalDateTime(String arrivalDateTime) {
    this.arrivalDateTime = arrivalDateTime;
  }

  public String getDepartureDateTime() {
    return departureDateTime;
  }

  public void setDepartureDateTime(String departureDateTime) {
    this.departureDateTime = departureDateTime;
  }

  public StopPointDTO getStopPoint() {
    return stopPoint;
  }

  public void setStopPoint(StopPointDTO stopPoint) {
    this.stopPoint = stopPoint;
  }

}
