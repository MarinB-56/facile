package org.acme.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JourneyDTO {
  @JsonProperty("duration") // Précise le nom tel qu'il est dans le json
  private int totalDuration;

  @JsonProperty("nb_transfers")
  private int nbTransfers;

  private DurationDTO durations;

  private List<LinkDTO> links;
  private List<SectionDTO> sections;

  public JourneyDTO() {}

  public int getTotalDuration() {
    return totalDuration;
  }

  public void setTotalDuration(int totalDuration) {
    this.totalDuration = totalDuration;
  }

  public int getNbTransfers() {
    return nbTransfers;
  }

  public void setNbTransfers(int nbTransfers) {
    this.nbTransfers = nbTransfers;
  }

  public DurationDTO getDurations() {
    return durations;
  }

  public void setDurations(DurationDTO durations) {
    this.durations = durations;
  }

  public List<LinkDTO> getLinks() {
    return links;
  }

  public void setLinks(List<LinkDTO> links) {
    this.links = links;
  }

  public List<SectionDTO> getSections() {
    return sections;
  }

  public void setSections(List<SectionDTO> sections) {
    this.sections = sections;
  }

  @Override
  // public String toString() {
  //   return "JourneyDTO : totalDuration = " + totalDuration + ", nbTransfers = " + nbTransfers + ", durations = " + durations
  //       + ", links = " + links + ", sections = " + sections;
  // }
  public String toString() {
    return "\n VOYAGE: " + sections + "\n";
  }

  public SectionDTO getJourneyLastSection(){
    // Récupération du nombre de sections
    int nbSections = getSections().size();

    // Renvoie de l'id de la dernière section
    return getSections().get(nbSections - 1);
  }
}
