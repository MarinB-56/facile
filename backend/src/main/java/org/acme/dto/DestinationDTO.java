package org.acme.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DestinationDTO {
  public String id;
  public String name;
  public String embedded_type;

  // public boolean isDeparture;

  public DestinationDTO(){} //Pour la deserialisation Json
}
