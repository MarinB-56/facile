package org.acme.service;

import org.acme.dto.JourneyDTO;
import org.acme.dto.SectionDTO;
import org.acme.dto.StopPointDTO;
import org.acme.dto.TripDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class BuildJourneyServiceTest {

  private TripDTO trip = new TripDTO();
  private BuildJourneyService buildJourneyService = new BuildJourneyService();
  private StopPointDTO departure = new StopPointDTO();
  private StopPointDTO arrival = new StopPointDTO();
  private Set<SectionDTO> gtfSectionProposals;

  private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");


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

  @Test
  void shouldDeleteBoatsArrivingTooLate(){
    // Simulation du sens du bateau
    boolean isBoatFirstSection = true;

    // Simulation des SectionDTO "Bateaux" récupérés via GTFS
    this.gtfSectionProposals = new HashSet<SectionDTO>();

    SectionDTO firstBoat = new SectionDTO();
    firstBoat.setArrivalDateTime("2025-10-30T10:30:00");
    this.gtfSectionProposals.add(firstBoat);

    SectionDTO secondBoat = new SectionDTO();
    secondBoat.setArrivalDateTime("2025-10-30T14:30:00");
    this.gtfSectionProposals.add(secondBoat);

    SectionDTO thirdBoat = new SectionDTO();
    thirdBoat.setArrivalDateTime("2025-10-30T15:00:00");
    this.gtfSectionProposals.add(thirdBoat);

    SectionDTO fourthBoat = new SectionDTO();
    fourthBoat.setArrivalDateTime("2025-10-30T20:00:00");
    this.gtfSectionProposals.add(fourthBoat);

    // Simulation d'un voyage dont le départ est à 15h et l'arrivée à 18h
    JourneyDTO journey = new JourneyDTO();

    SectionDTO firstSection = new SectionDTO();
    firstSection.setDepartureDateTime("20251030T150000");

    SectionDTO lastSection = new SectionDTO();
    lastSection.setArrivalDateTime("20251030T180000");

    journey.getSections().add(firstSection);
    journey.getSections().add(lastSection);

    // Filtre des bateaux compatibles avec le départ du train
    Set<SectionDTO> filteredBoatSections = this.buildJourneyService.getCompatibleBoats(this.gtfSectionProposals, journey, isBoatFirstSection);

    // Liste de bateaux attendue après le filtre
    Set<SectionDTO> expectedFilteredBoatSections = new HashSet<SectionDTO>();
    expectedFilteredBoatSections.add(firstBoat);
    expectedFilteredBoatSections.add(secondBoat);

    // Comparaison
    assertEquals(expectedFilteredBoatSections, filteredBoatSections, "Certains bateaux ne sont pas compatibles");
  }

  @Test
  void shouldDeleteBoatsLeavingTooEary(){
    // Simulation du sens du bateau
    boolean isBoatFirstSection = false;

    // Simulation des SectionDTO "Bateaux" récupérés via GTFS
    this.gtfSectionProposals = new HashSet<SectionDTO>();

    SectionDTO firstBoat = new SectionDTO();
    firstBoat.setDepartureDateTime("2025-10-30T10:30:00");
    this.gtfSectionProposals.add(firstBoat);

    SectionDTO secondBoat = new SectionDTO();
    secondBoat.setDepartureDateTime("2025-10-30T15:30:00");
    this.gtfSectionProposals.add(secondBoat);

    SectionDTO thirdBoat = new SectionDTO();
    thirdBoat.setDepartureDateTime("2025-10-30T18:00:00");
    this.gtfSectionProposals.add(thirdBoat);

    SectionDTO fourthBoat = new SectionDTO();
    fourthBoat.setDepartureDateTime("2025-10-30T20:00:00");
    this.gtfSectionProposals.add(fourthBoat);

    // Simulation d'un voyage dont le départ est à 10h et l'arrivée à 15h
    JourneyDTO journey = new JourneyDTO();

    SectionDTO firstSection = new SectionDTO();
    firstSection.setDepartureDateTime("20251030T100000");

    SectionDTO lastSection = new SectionDTO();
    lastSection.setArrivalDateTime("20251030T150000");

    journey.getSections().add(firstSection);
    journey.getSections().add(lastSection);

    // Filtre des bateaux compatibles avec le départ du train
    Set<SectionDTO> filteredBoatSections = this.buildJourneyService.getCompatibleBoats(this.gtfSectionProposals, journey, isBoatFirstSection);

    // Liste de bateaux attendue après le filtre
    Set<SectionDTO> expectedFilteredBoatSections = new HashSet<SectionDTO>();
    expectedFilteredBoatSections.add(secondBoat);
    expectedFilteredBoatSections.add(thirdBoat);
    expectedFilteredBoatSections.add(fourthBoat);

    // Comparaison
    assertEquals(expectedFilteredBoatSections, filteredBoatSections, "Certains bateaux ne sont pas compatibles");
  }

  @Test
  void shouldReturnMostOptimizedBoat(){
    boolean isBoatFirstSection = true;

    // Simulation de deux voyages compatibles avec le trajet en train
    Set<SectionDTO> compatibleBoats = new HashSet<>();

    SectionDTO firstCompatibleBoat = new SectionDTO();
    firstCompatibleBoat.setDepartureDateTime("2025-10-30T10:30:00");
    firstCompatibleBoat.setArrivalDateTime("2025-10-30T11:20:00");

    compatibleBoats.add(firstCompatibleBoat);

    SectionDTO secondCompatibleBoat = new SectionDTO();
    secondCompatibleBoat.setDepartureDateTime("2025-10-30T15:30:00");
    secondCompatibleBoat.setArrivalDateTime("2025-10-30T16:20:00");

    compatibleBoats.add(secondCompatibleBoat);

    System.out.println(compatibleBoats.size());

    // Simulation d'un voyage en train dont le départ est à 17h et l'arrivée à 19h
    JourneyDTO journey = new JourneyDTO();

    SectionDTO firstSection = new SectionDTO();
    firstSection.setDepartureDateTime("20251030T170000");
    SectionDTO lastSection = new SectionDTO();
    lastSection.setArrivalDateTime("20251030T190000");

    journey.getSections().add(firstSection);
    journey.getSections().add(lastSection);

    SectionDTO mostOptimizedBoat = this.buildJourneyService.getMostOptimizedBoat(compatibleBoats, journey, isBoatFirstSection);
    SectionDTO expectedOptimizedBoat = secondCompatibleBoat;

    assertEquals(expectedOptimizedBoat, mostOptimizedBoat, "Le bateau choisi n'est pas le plus optimisé");
  }
}

/*
 * Pour chaque section de bateau
 *  // Je supprime les bateaux qui ne sont pas compatibles avec le trajet recherché
 *  //
 */
