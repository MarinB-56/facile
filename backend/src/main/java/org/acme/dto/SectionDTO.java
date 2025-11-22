package org.acme.dto;

import java.util.ArrayList;
import java.util.List;

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

  private String type;

  @JsonProperty("display_informations")
  private DisplayInformationsDTO displayInformations;

  @JsonProperty("stop_date_times")
  private List<StopDateTimeDTO> stopDateTimeDTOs = new ArrayList<>();;

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
    return this.from;
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

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public DisplayInformationsDTO getDisplayInformationsDTO(){
    return this.displayInformations;
  }

  public void setDisplayInformationsDTO(DisplayInformationsDTO displayInformations){
    this.displayInformations = displayInformations;
  }

  @Override
  // public String toString() {
  //   return "SectionDTO : SectionDuration = " + SectionDuration + ", departureDateTime = " + departureDateTime
  //       + ", arrivalDateTime = " + arrivalDateTime + ", from = " + from + ", to = " + to + ", type = " + type ;
  // }
  public String toString() {
    return "\n -" + from + " to " + to + ", " + departureDateTime + " - " + arrivalDateTime;
  }

}
