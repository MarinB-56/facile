package org.acme;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "trips")
public class Trip {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "departure_date", nullable = false)
  private LocalDateTime departureDate;

  @Column(name = "arrival_date", nullable = false)
  private LocalDateTime arrivalDate;

  @Column(name = "connections_count", nullable = false)
  private int connectionsCount;

  @Column(name = "departure_location")
  private String departureLocation;

  @Column(name = "arrival_location")
  private String arrivalLocation;

  @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true )
  private List<Subtrip> subtrips = new ArrayList<>();

  // Getters and setters
  public long getId() {
    return id;
  }

  public LocalDateTime getDepartureDate() {
    return departureDate;
  }

  public void setDepartureDate(LocalDateTime departureDate) {
    this.departureDate = departureDate;
  }

  public LocalDateTime getArrivalDate() {
    return arrivalDate;
  }

  public void setArrivalDate(LocalDateTime arrivalDate) {
    this.arrivalDate = arrivalDate;
  }

  public int getConnectionsCount() {
    return connectionsCount;
  }

  public void setConnectionsCount(int connectionsCount) {
    this.connectionsCount = connectionsCount;
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

  public List<Subtrip> getSubtrips() {
    return subtrips;
  }

  public void setSubtrips(List<Subtrip> subtrips) {
    this.subtrips = subtrips;
  }

  // Methods
  @Transient
  @JsonIgnore // à retirer si tu veux l'afficher dans les API
  public long getDuration() {
    return ChronoUnit.MINUTES.between(departureDate, arrivalDate);
  }

  @Override
  public String toString() {
    return "Trip from " + getDepartureLocation() +
          " to " + getArrivalLocation() +
          " from " + getDepartureDate() +
          " to " + getArrivalDate() +
          ", duration: " + getDuration() + "minutes" +
          ", connections: " + getConnectionsCount();
  }

  public void addSubtrip(Subtrip subtrip) {
    subtrip.setTrip(this);
    subtrips.add(subtrip);
  }
}
