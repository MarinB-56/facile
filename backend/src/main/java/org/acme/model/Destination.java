package org.acme.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Destination {
  public String id;
  public String name;
  public String embedded_type;

  public Destination(){} //Pour la deserialisation Json
}
