package org.acme;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// @Entity
//@Table(name = "destinations")
public class Destination {
  // Attributs
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String name;
  private double latitude;
  private double longitude;

  // Constructors
  public Destination(){}

  public Destination(String name, double latitude, double longitude){
    this.name = name;
    this.latitude = latitude;
    this.longitude = longitude;
  }

  // Getters and Setters
  public long getId(){
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

}
