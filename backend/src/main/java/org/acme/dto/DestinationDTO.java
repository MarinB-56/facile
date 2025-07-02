package org.acme.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DestinationDTO {
  public String id;
  public String name;
  public String embedded_type;

  // public boolean isDeparture;

  public DestinationDTO(){} //Pour la deserialisation Json

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmbedded_type() {
    return embedded_type;
  }

  public void setEmbedded_type(String embedded_type) {
    this.embedded_type = embedded_type;
  }

  @Override
  public String toString() {
    return "DestinationDTO : " +" id = '" + id + '\'' +", name = '" + name + '\'' +", embedded_type = '" + embedded_type + '\'';
  }

}
