package org.acme.service;

import org.acme.dto.StopPointDTO;
import org.acme.dto.TripDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class BuildJourneyServiceTest {

  private TripDTO trip = new TripDTO();
  private BuildJourneyService buildJourneyService = new BuildJourneyService();
  private StopPointDTO departure = new StopPointDTO();
  private StopPointDTO arrival = new StopPointDTO();

  @Test
  void shouldReturnBoatAsFirstSection(){
    this.departure.setName("Quiberon");
    this.arrival.setName("Autre gare");

    this.trip.setArrival(this.arrival);
    this.trip.setDeparture(this.departure);

    boolean isBoatFirstSection = this.buildJourneyService.getIfBoatIsFirstSection(this.trip);

    assertEquals(isBoatFirstSection, true, "La première étape du trajet n'est pas la bonne");
  }

  @Test
  void shouldReturnBoatAsLastSection(){

    this.departure.setName("Autre gare");
    this.arrival.setName("Quiberon");

    this.trip.setDeparture(this.departure);
    this.trip.setArrival(this.arrival);

    boolean isBoatFirstSection = this.buildJourneyService.getIfBoatIsFirstSection(this.trip);

    assertEquals(isBoatFirstSection, false, "La première étape du trajet n'est pas la bonne");
  }

  
}
