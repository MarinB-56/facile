package org.acme;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "trips")
public class Trip {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "departure_date", nullable = false)
  private LocalDate departureDate;

  @Column(name = "arrival_date", nullable = false)
  private LocalDate arrivalDate;

  @Column(name = "connections_count", nullable = false)
  private int connectionsCount;

  @ManyToOne
  @JoinColumn(name = "departure_destination_id", nullable = false)
  private Destination departureDestination;

  @ManyToOne
  @JoinColumn(name = "arrival_destination_id", nullable = false)
  private Destination arrivalDestination;


  // Getters and setters
  public long getId() {
    return id;
  }

  public LocalDate getDepartureDate() {
    return departureDate;
  }

  public void setDepartureDate(LocalDate departureDate) {
    this.departureDate = departureDate;
  }

  public LocalDate getArrivalDate() {
    return arrivalDate;
  }

  public void setArrivalDate(LocalDate arrivalDate) {
    this.arrivalDate = arrivalDate;
  }

  public long getDuration() {
    return duration;
  }

  public void setDuration(long duration) {
    this.duration = duration;
  }

  public int getConnectionsCount() {
    return connectionsCount;
  }

  public void setConnectionsCount(int connectionsCount) {
    this.connectionsCount = connectionsCount;
  }

  public Destination getDepartureDestination() {
    return departureDestination;
  }

  public void setDepartureDestination(Destination departureDestination) {
    this.departureDestination = departureDestination;
  }

  public Destination getArrivalDestination() {
    return arrivalDestination;
  }

  public void setArrivalDestination(Destination arrivalDestination) {
    this.arrivalDestination = arrivalDestination;
  }
}
