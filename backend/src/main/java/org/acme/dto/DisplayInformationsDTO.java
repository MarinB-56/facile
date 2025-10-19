package org.acme.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DisplayInformationsDTO {

  @JsonProperty("commercial_mode")
  private String commercialMode;

  private String network;
  private String company;

  @JsonProperty("physical_mode")
  private String physicalMode;

  public String getCommercialMode(){
    return this.commercialMode;
  }

  public void setCommercialMode(String commercialMode){
    this.commercialMode = commercialMode;
  }

  public String getNetwork(){
    return this.network;
  }

  public void setNetwork(String network){
    this.network = network;
  }

  public String getCompany(){
    return this.company;
  }

  public void setCompany(String company){
    this.company = company;
  }

  public String getPhysicalMode(){
    return this.physicalMode;
  }

  public void setPhysicalMode(String physicalMode){
    this.physicalMode = physicalMode;
  }
}
