package org.acme.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JourneyProposalsDTO {
  @JsonProperty("journeys")
  private List<JourneyDTO> journeyProposals = new ArrayList<>();

  public JourneyProposalsDTO(){}

  public List<JourneyDTO> getJourneyProposals() {
    return journeyProposals;
  }

  public void setJourneyProposals(List<JourneyDTO> journeyProposals) {
    this.journeyProposals = journeyProposals;
  }

  @Override
  public String toString() {
    return "" + journeyProposals ;
  }
}
