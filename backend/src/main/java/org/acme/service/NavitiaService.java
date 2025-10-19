package org.acme.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
  private static final String TIMEFRAME_DURATION = "86400";

  @Inject
  @RestClient
  NavitiaClient navitiaClient;

  /**
   * Récupère les lieux possibles suite à l'input utilisateur.
   * @param query: l'input utilisateur (le début d'un lieu).
   * @return Une liste de propositions de lieu.
   */
  public String getAllLocations(String query){
    try {
      return navitiaClient.getAllLocations(query);
    } catch (Exception e) {
      System.err.println(e);
      return "NaN";
    }
  }

  /**
   * Récupère les lieux possibles suite à l'input utilisateur, accepte une option supplémentaire (ex: only stop point)
   * @param query: l'input utilisateur (le début d'un lieu).
   * @return Une liste de propositions de lieu.
   */
  public String getAllLocations(String query, String type){
    try {
      return navitiaClient.getAllLocations(query, type);
    } catch (Exception e) {
      System.err.println(e);
      return "NaN";
    }
  }

  /**
    * Récupère une liste d'itinéraires possibles en fonction d'un lieu de départ, d'arrivée et d'une date
    * @param query: Lieu de départ, lieu d'arrivée et date du voyage
    * @return une liste de propositions d'itinéraires possibles
    */
  // Itinary research
  public JourneyProposalsDTO getItineraries(TripDTO trip){
    System.out.println("Date du voyage recherché : " + trip.getDate());
    // Récupération des propositions brut (trip pour la journée)
    JourneyProposalsDTO journeyProposals = getItinerariesProposals(trip);

    System.out.println("Nombre de journey trouvés: " + journeyProposals.getJourneyProposals().size() );

    //Vérification si passe par Paris
    if(containsStopAtParis(journeyProposals)){
      System.out.println("Par Paris");
      // Construction des trips en passant par Paris
      JourneyProposalsDTO journeyProposalsThroughParis = getItinerariesThroughParis(trip);
      // Fusion de toutes les propositions de voyage
      if(journeyProposalsThroughParis != null){
        journeyProposals.getJourneyProposals().addAll(journeyProposalsThroughParis.getJourneyProposals());
      }
    }

    // Construction de trajets en passant par Paris (arbitraire)
    /*
     * TODO:
     * - Vérifier automatiquement si le trajet a besoin de passer par Paris ou non
     */
    // JourneyProposalsDTO journeyProposalsThroughParis = getItinerariesThroughParis(trip);
    // if(journeyProposalsThroughParis != null){
    //   journeyProposals.getJourneyProposals().addAll(journeyProposalsThroughParis.getJourneyProposals());
    // }

    System.out.println("Nombre de journey par Paris trouvés: " + journeyProposals.getJourneyProposals().size() );

    // Tri des voyages dans l'ordre de départ
    journeyProposals.getJourneyProposals().sort(Comparator.comparing(
      JourneyDTO::getFirstDeparturDateTime,
      Comparator.nullsLast(Comparator.naturalOrder())
    ));

    // Affichage des voyages
    // for(JourneyDTO journey : journeyProposals.getJourneyProposals()){
    //   System.out.println(journey.getFirstDeparturDateTime());
    // }

    return journeyProposals;
  }

  // Journey builder
  private JourneyProposalsDTO getItinerariesProposals(TripDTO trip){
    String idDeparture = trip.getDeparture().getId();
    String idArrival = trip.getArrival().getId();
    String tripDate = trip.getDate().toString();
    String datetimeRepresents = "departure";

    JourneyProposalsDTO journeyProposals = new JourneyProposalsDTO();

    // Récupération des propositions brut (itinéraires de toute la journée)
    // Appel à l'API Navitia
    try {
      journeyProposals = navitiaClient.getItinerariesProposals(idDeparture, idArrival, tripDate, datetimeRepresents, TIMEFRAME_DURATION);
      journeyProposals = cleanJourneyProposals(journeyProposals); // Suppression des crow_fly

    } catch (Exception e) {

      System.out.println(e);
    }

    return journeyProposals;
  }

  // cleaner
  private JourneyProposalsDTO cleanJourneyProposals(JourneyProposalsDTO journeyProposals){

    for (JourneyDTO journey : journeyProposals.getJourneyProposals()) {
      journey.getSections().removeIf(section ->
        section.getType() != null && section.getType().equalsIgnoreCase("crow_fly")
      );
    }

    return journeyProposals;
  }

  // transfer compatibility
  private boolean containsStopAtParis(JourneyProposalsDTO journeyProposals){

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

  // journey builder
  private JourneyProposalsDTO getItinerariesThroughParis(TripDTO trip){
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
    try {

      JourneyProposalsDTO journeyProposalsToParis = navitiaClient.getItinerariesProposals(idDeparture, PARIS_REGION_ID, tripDate, datetimeRepresents, TIMEFRAME_DURATION);
      JourneyProposalsDTO journeyProposalsFromParis = navitiaClient.getItinerariesProposals(PARIS_REGION_ID, idArrival, tripDate, datetimeRepresents, TIMEFRAME_DURATION);

      // Nettoyage des propositions (suppression des crow_fly)
      journeyProposalsToParis = cleanJourneyProposals(journeyProposalsToParis);
      journeyProposalsFromParis = cleanJourneyProposals(journeyProposalsFromParis);

      // Construction des itinéraires du départ -> à Paris -> à l'arrivée
      JourneyProposalsDTO journeyProposalsThroughParis = joinJourneyProposals(journeyProposalsToParis, journeyProposalsFromParis);

      return journeyProposalsThroughParis;
    } catch (Exception e) {
      System.err.println(e);
      return new JourneyProposalsDTO();
    }


  }

  // Journey builder
  private JourneyProposalsDTO  joinJourneyProposals(JourneyProposalsDTO journeyProposalsToParis, JourneyProposalsDTO journeyProposalsFromParis){
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

          // System.out.println(journeyToParis.getJourneyLastSection().toString());
          // System.out.println(journeyFromParis.getSections().get(0).toString());

          // System.out.println("------ TRAJETS COMPATIBLES ------");

          // Ajout du trajet construit à la liste des trajets qui passent par Paris
          journeyProposalsThroughParis.getJourneyProposals().add(journeyThroughParis);
          break;
        }
      }
    }

    return journeyProposalsThroughParis;
  }

  // tranfer compatibility
  private TransferCompatibility checkTransferCompatibility(JourneyDTO firstJourney, JourneyDTO secondJourney){

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

  // transfer compatibility
  private int getSectionDuration(String firstDateTime, String secondDateTime){

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");

    LocalDateTime arrivalDateTime = LocalDateTime.parse(firstDateTime, formatter);
    LocalDateTime departureDateTime = LocalDateTime.parse(secondDateTime, formatter);
    long transferDurationInSeconds = Duration.between(arrivalDateTime, departureDateTime).toSeconds();

    return (int) transferDurationInSeconds;
  }

  // transfer compatibility
  private boolean areSectionsCompatibles(SectionDTO lastArrivalSection, SectionDTO firstDepartureSection, boolean areSameStations, long transferDuration){
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

  // journey builder
  private JourneyDTO buildJourneyThroughParis(JourneyDTO journeyToParis, JourneyDTO journeyFromParis, TransferCompatibility compatibilityCheckResult){
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

  // journey builder
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

  // transfer compatibility
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

  // journey builder
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

  // journey builder
  private int computeNbTransfers(List<SectionDTO> sections){
    // Pour chaque section avec au moins 1 public transport, on ajoute 1;
    // On initialise à -1 (0 transfert pour un trajet direct)
    int nbTransfers = -1 ; //
    for(SectionDTO section : sections){
      if(section.getType().equals("public_transport")){
        nbTransfers++;
      }
    }
    return nbTransfers;
  }

  // journey builder
  private List<LinkDTO> joinLinks(JourneyDTO firstJourney, JourneyDTO secondJourney){
    List<LinkDTO> links = new ArrayList<>();

    links.addAll(firstJourney.getLinks());
    links.addAll(secondJourney.getLinks());

    return links;
  }

}
