package org.acme.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SectionDTO {
  @JsonProperty("duration")
  private int SectionDuration;

  @JsonProperty("departure_date_time")
  private String departureDateTime;

  @JsonProperty("arrival_date_time")
  private String arrivalDateTime;

  private StopPointDTO from;
  private StopPointDTO to;

  // private Mode mode;

  public SectionDTO(){}

  public int getSectionDuration() {
    return SectionDuration;
  }

  public void setSectionDuration(int sectionDuration) {
    SectionDuration = sectionDuration;
  }

  public String getDepartureDateTime() {
    return departureDateTime;
  }

  public void setDepartureDateTime(String departureDateTime) {
    this.departureDateTime = departureDateTime;
  }

  public String getArrivalDateTime() {
    return arrivalDateTime;
  }

  public void setArrivalDateTime(String arrivalDateTime) {
    this.arrivalDateTime = arrivalDateTime;
  }

  public StopPointDTO getFrom() {
    return from;
  }

  public void setFrom(StopPointDTO from) {
    this.from = from;
  }

  public StopPointDTO getTo() {
    return to;
  }

  public void setTo(StopPointDTO to) {
    this.to = to;
  }

  // public Mode getMode() {
  //   return mode;
  // }

  // public void setMode(Mode mode) {
  //   this.mode = mode;
  // }

  @Override
  public String toString() {
    return "SectionDTO : SectionDuration = " + SectionDuration + ", departureDateTime = " + departureDateTime
        + ", arrivalDateTime = " + arrivalDateTime + ", from = " + from + ", to = " + to ;
  }


}
