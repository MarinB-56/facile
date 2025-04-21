package org.acme.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "subtrips")
public class Subtrip {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY )
  private long id;

  @Column(name = "departure_location", nullable = false)
  private String departureLocation;

  @Column(name = "arrival_location", nullable = false)
  private String arrivalLocation;

  @Column(name = "departure_date", nullable = false)
  private LocalDateTime departureDate;

  @Column(name = "arrival_date", nullable = false)
  private LocalDateTime arrivalDate;

  @Column(name = "step_order")
  private int stepOrder;

  private String info;

  @ManyToOne
  @JoinColumn(name = "trip_id", nullable = false)
  private Trip trip;

  public long getId() {
    return id;
  }

  public String getDepartureLocation() {
    return departureLocation;
  }

  public void setDepartureLocation(String departureLocation) {
    this.departureLocation = departureLocation;
  }

  public String getArrivalLocation() {
    return arrivalLocation;
  }

  public void setArrivalLocation(String arrivalLocation) {
    this.arrivalLocation = arrivalLocation;
  }

  public LocalDateTime getDepartureDate() {
    return departureDate;
  }

  public void setDepartureDate(LocalDateTime departure_date) {
    this.departureDate = departure_date;
  }

  public LocalDateTime getArrivalDate() {
    return arrivalDate;
  }

  public void setArrivalDate(LocalDateTime arrival_date) {
    this.arrivalDate = arrival_date;
  }

  public int getStepOrder() {
    return stepOrder;
  }

  public void setStepOrder(int stepOrder) {
    this.stepOrder = stepOrder;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  public Trip getTrip() {
    return trip;
  }

  public void setTrip(Trip trip) {
    this.trip = trip;
  }
}
