package org.acme.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StopPointDTO {
  public String id;
  public String name;

  @JsonProperty("embedded_type")
  public String embeddedType;

  public StopPointDTO(){} //Pour la deserialisation Json

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

  public String getEmbeddedType() {
    return embeddedType;
  }

  public void setEmbeddedType(String embeddedType) {
    this.embeddedType = embeddedType;
  }

  @Override
  public String toString() {
    return "StopPointDTO : " +" id = '" + id + '\'' +", name = '" + name + '\'' +", embeddedType = '" + embeddedType + '\'';
  }

}
