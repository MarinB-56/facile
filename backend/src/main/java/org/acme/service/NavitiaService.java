package org.acme.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.acme.client.NavitiaClient;
import org.acme.dto.DurationDTO;
import org.acme.dto.JourneyDTO;
import org.acme.dto.JourneyProposalsDTO;
import org.acme.dto.LinkDTO;
import org.acme.dto.SectionDTO;
import org.acme.dto.StopPointDTO;
import org.acme.dto.TransferCompatibility;
import org.acme.dto.TripDTO;
import org.eclipse.microprofile.rest.client.inject.RestClient;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class NavitiaService {

  private static final String PARIS_REGION_ID = "admin:fr:75056";
;  private static final String TIMEFRAME_DURATION = "86400";

  @Inject
  @RestClient
  NavitiaClient navitiaClient;

  // Locations input autocomplete
  public String getAllLocations(String query){
    return navitiaClient.getAllLocations(query);
  }

  // Locations input autocomplete (with possible filter (ex: only stop point))
  public String getAllLocations(String query, String type){
    return navitiaClient.getAllLocations(query, type);
  }

  // Itinary research
  public JourneyProposalsDTO getItineraries(TripDTO trip){
    // Récupération des propositions brut (trip pour la journée)
    JourneyProposalsDTO journeyProposals = getItinerariesProposals(trip);

    // Vérification si passe par Paris
    // if(containsStopAtParis(journeyProposals)){
    //   System.out.println("Par Paris");
    //   // Construction des trips en passant par Paris
    //   JourneyProposalsDTO journeyProposalsThroughParis = getItinerariesThroughParis(trip);

    //   // Fusion de toutes les propositions de voyage
    //   journeyProposals.getJourneyProposals().addAll(journeyProposalsThroughParis.getJourneyProposals());
    // }

    JourneyProposalsDTO journeyProposalsThroughParis = getItinerariesThroughParis(trip);
    journeyProposals.getJourneyProposals().addAll(journeyProposalsThroughParis.getJourneyProposals());

    return journeyProposals;
  }

  // Récupération des données brut
  public JourneyProposalsDTO getItinerariesProposals(TripDTO trip){
    String idDeparture = trip.getDeparture().getId();
    String idArrival = trip.getArrival().getId();
    String tripDate = trip.getDate().toString();
    String datetimeRepresents = "departure";

    // Récupération des propositions brut (itinéraires de toute la journée)
    // Appel à l'API Navitia
    JourneyProposalsDTO journeyProposals = navitiaClient.getItinerariesProposals(idDeparture, idArrival, tripDate, datetimeRepresents, TIMEFRAME_DURATION);

    // On supprime les crow_fly
    journeyProposals = cleanJourneyProposals(journeyProposals);

    // System.out.println(journeyProposals.toString());

    return journeyProposals;
  }

  public JourneyProposalsDTO cleanJourneyProposals(JourneyProposalsDTO journeyProposals){

    // System.out.println("=== AVANT CLEAN ===");
    // for (JourneyDTO journey : journeyProposals.getJourneyProposals()) {
    //   for (SectionDTO section : journey.getSections()) {
    //     System.out.println("Section type: " + section.getType());
    //   }
    // }

    for (JourneyDTO journey : journeyProposals.getJourneyProposals()) {
      journey.getSections().removeIf(section ->
        section.getType() != null && section.getType().equalsIgnoreCase("crow_fly")
      );
    }

    // System.out.println("=== Après CLEAN ===");
    // for (JourneyDTO journey : journeyProposals.getJourneyProposals()) {
    //   for (SectionDTO section : journey.getSections()) {
    //     System.out.println("Section type: " + section.getType());
    //   }
    // }

    return journeyProposals;
  }

  // Vérification si le trajet a un stop à Paris
  public boolean containsStopAtParis(JourneyProposalsDTO journeyProposals){

    for(JourneyDTO journeyProposal : journeyProposals.getJourneyProposals()){
      for( SectionDTO section : journeyProposal.getSections() ){

        StopPointDTO from = section.getFrom();
        StopPointDTO to = section.getTo();

        if (from != null && from.getName() != null && from.getName().contains("Paris")) {
          return true;
        }

        if (to != null && to.getName() != null && to.getName().contains("Paris")) {
          return true;
        }
      }
    }

    return false;
  }

  public JourneyProposalsDTO getItinerariesThroughParis(TripDTO trip){
    // On trouve les informations du Trip
    String idDeparture = trip.getDeparture().getId();
    String idArrival = trip.getArrival().getId();
    String nameDeparture = trip.getDeparture().getName();
    String nameArrival = trip.getArrival().getName();
    String tripDate = trip.getDate().toString();
    String datetimeRepresents = "departure";

    // Si le trajet passe déjà par Paris, pas besoin de rechercher
    if(nameDeparture.contains("Paris") || nameArrival.contains("Paris")){
      return new JourneyProposalsDTO();
    }

    // Itinéraires: du départ -> à PARIS
    JourneyProposalsDTO journeyProposalsToParis = navitiaClient.getItinerariesProposals(idDeparture, PARIS_REGION_ID, tripDate, datetimeRepresents, TIMEFRAME_DURATION);
    // Itinéraires: de PARIS -> à l'arrivée
    JourneyProposalsDTO journeyProposalsFromParis = navitiaClient.getItinerariesProposals(PARIS_REGION_ID, idArrival, tripDate, datetimeRepresents, TIMEFRAME_DURATION);

    // Nettoyage des propositions (suppression des crow_fly)
    journeyProposalsToParis = cleanJourneyProposals(journeyProposalsToParis);
    journeyProposalsFromParis = cleanJourneyProposals(journeyProposalsFromParis);

    // System.out.println(journeyProposalsToParis.toString());
    // System.out.println(journeyProposalsFromParis.toString());

    // Construction des itinéraires du départ -> à Paris -> à l'arrivée
    JourneyProposalsDTO journeyProposalsThroughParis = joinJourneyProposals(journeyProposalsToParis, journeyProposalsFromParis);

    return journeyProposalsThroughParis;
  }

  public JourneyProposalsDTO  joinJourneyProposals(JourneyProposalsDTO journeyProposalsToParis, JourneyProposalsDTO journeyProposalsFromParis){
    JourneyProposalsDTO journeyProposalsThroughParis = new JourneyProposalsDTO();
    journeyProposalsThroughParis.setJourneyProposals(new ArrayList<>());

    // Pour chaque journey vers Paris
    for(JourneyDTO journeyToParis : journeyProposalsToParis.getJourneyProposals()){
      // Pour chaque trajet au départ de Paris
      for(JourneyDTO journeyFromParis : journeyProposalsFromParis.getJourneyProposals()){

        // Check compatibilité entre les deux trajets
        TransferCompatibility compatibilityCheckResult = checkTransferCompatibility(journeyToParis, journeyFromParis);

        // Si trajets compatibles
        if(compatibilityCheckResult.isTransferPossible()){
          JourneyDTO journeyThroughParis = buildJourneyThroughParis(journeyToParis, journeyFromParis, compatibilityCheckResult);

          System.out.println(journeyToParis.getJourneyLastSection().toString());
          System.out.println(journeyFromParis.getSections().get(0).toString());

          System.out.println("------ TRAJETS COMPATIBLES ------");

          // Ajout du trajet construit à la liste des trajets qui passent par Paris
          journeyProposalsThroughParis.getJourneyProposals().add(journeyThroughParis);
          break;
        }
      }
    }

    return journeyProposalsThroughParis;
  }

  public TransferCompatibility checkTransferCompatibility(JourneyDTO firstJourney, JourneyDTO secondJourney){

    SectionDTO lastArrivalSection = firstJourney.getJourneyLastSection();
    SectionDTO firstDepartureSection = secondJourney.getSections().get(0);

    // Same station ?
    StopPointDTO arrivalStation = lastArrivalSection.getTo();
    StopPointDTO departureStation = firstDepartureSection.getFrom();
    boolean areSameStations = arrivalStation.getId().equals(departureStation.getId());

    // Time between arrival and departure
    long transferDurationInSeconds = getSectionDuration( lastArrivalSection.getArrivalDateTime(), firstDepartureSection.getDepartureDateTime());

    // Compatible ?
    boolean isTransferPossible = areSectionsCompatibles(lastArrivalSection, firstDepartureSection, areSameStations, transferDurationInSeconds);

    return new TransferCompatibility(isTransferPossible, areSameStations, transferDurationInSeconds, lastArrivalSection, firstDepartureSection);
  }

  public int getSectionDuration(String firstDateTime, String secondDateTime){

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");

    LocalDateTime arrivalDateTime = LocalDateTime.parse(firstDateTime, formatter);
    LocalDateTime departureDateTime = LocalDateTime.parse(secondDateTime, formatter);
    long transferDurationInSeconds = Duration.between(arrivalDateTime, departureDateTime).toSeconds();

    return (int) transferDurationInSeconds;
  }

  public boolean areSectionsCompatibles(SectionDTO lastArrivalSection, SectionDTO firstDepartureSection, boolean areSameStations, long transferDuration){
    if(transferDuration < 600){ // Moins de 10 minutes
      return false;
    } else if(areSameStations && transferDuration >= 600){ // Plus de 10 minutes
      return true;
    } else if(transferDuration >= 3000){ // Plus de 50 minutes
      return true;
    }

    // Par défaut
    return false;
  }

  public JourneyDTO buildJourneyThroughParis(JourneyDTO journeyToParis, JourneyDTO journeyFromParis, TransferCompatibility compatibilityCheckResult){
    // - Si compatibles, les assembler (trajet1, trajet2, temps entre les deux, même station)
    // - Création d'un voyage général (JourneyDTO)
    JourneyDTO newJourney = new JourneyDTO();

    // Ajout de la durée totale du voyage
    int totalDuration = computeTotalDuration(journeyToParis.getTotalDuration(), journeyFromParis.getTotalDuration(), compatibilityCheckResult);
    newJourney.setTotalDuration(totalDuration);

    // Ajout des sections du voyage
    List<SectionDTO> joinedSections = joinSections(journeyToParis, journeyFromParis, compatibilityCheckResult);
    newJourney.setSections(joinedSections);

    // Ajout des durées totales et walking
    DurationDTO durations = computeDurations(joinedSections, totalDuration);
    newJourney.setDurations(durations);

    // Nombre de transfert
    int nbTransfers = computeNbTransfers(joinedSections);
    newJourney.setNbTransfers(nbTransfers);

    // Ajout des liens
    List<LinkDTO> links = joinLinks(journeyToParis, journeyFromParis);
    newJourney.setLinks(links);

    return newJourney;
  }

  private List<SectionDTO> joinSections(JourneyDTO journeyToParis, JourneyDTO journeyFromParis, TransferCompatibility compatibilityCheckResult){
    List<SectionDTO> sections = new ArrayList<>();

    // Ajout des sections trajet 1
    sections.addAll(journeyToParis.getSections());

    // Ajout d'une section au milieu (pour le transfert)
    SectionDTO transferSection = buildTransferSection(compatibilityCheckResult);
    sections.add(transferSection);

    // Ajout des sections trajet 2
    sections.addAll(journeyFromParis.getSections());

    return sections;
  }

  private SectionDTO buildTransferSection(TransferCompatibility compatibilityCheckResult){
    SectionDTO newSection = new SectionDTO();

    int sectionDuration = (int) compatibilityCheckResult.transferDurationInSeconds();
    String arrivalDateTime = compatibilityCheckResult.arrivalSection().getArrivalDateTime();
    String departureDateTime = compatibilityCheckResult.departureSection().getDepartureDateTime();
    StopPointDTO from = compatibilityCheckResult.arrivalSection().getTo();
    StopPointDTO to = compatibilityCheckResult.departureSection().getFrom();
    String type = "Walking";

    newSection.setSectionDuration(sectionDuration);
    newSection.setArrivalDateTime(arrivalDateTime);
    newSection.setDepartureDateTime(departureDateTime);
    newSection.setFrom(from);
    newSection.setTo(to);
    newSection.setType(type);

    return newSection;
  }

  private int computeTotalDuration(int firstSectionDuration, int secondSectionDuration, TransferCompatibility compatibilityCheckResult){
    long totalDuration = (int) firstSectionDuration + secondSectionDuration + compatibilityCheckResult.transferDurationInSeconds();

    return (int) totalDuration;
  }

  private DurationDTO computeDurations(List<SectionDTO> joinedSections, int totalDuration){
    DurationDTO duration = new DurationDTO();

    duration.setTotal(totalDuration);
    int walkingDuration = 0;

    for(SectionDTO section : joinedSections){
      if(section.getType().equals("Walking")){
        // Ajout de la durée de chaque section walking
        walkingDuration += getSectionDuration(section.getDepartureDateTime(), section.getArrivalDateTime());
      }
    }
    duration.setWalking(walkingDuration);
    return duration;
  }

  private int computeNbTransfers(List<SectionDTO> sections){
    return sections.size() - 1;
  }

  private List<LinkDTO> joinLinks(JourneyDTO firstJourney, JourneyDTO secondJourney){
    List<LinkDTO> links = new ArrayList<>();

    links.addAll(firstJourney.getLinks());
    links.addAll(secondJourney.getLinks());

    return links;
  }

}
