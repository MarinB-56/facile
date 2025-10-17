package org.acme.service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.acme.dto.DurationDTO;
import org.acme.dto.JourneyDTO;
import org.acme.dto.JourneyProposalsDTO;
import org.acme.dto.TripDTO;
import org.acme.dto.SectionDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class BuildJourneyService {

  private int MIN_TRANSFERT_TIME_IN_SECONDS = 1500; // = 25 minutes
  private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");

  @Inject
  NavitiaService navitiaService;

  @Inject
  GtfsService gtfsService;

  public JourneyProposalsDTO buildJourney(TripDTO trip){
    // Création de trajets via Navitia
    JourneyProposalsDTO navitiaJourneyProposals = navitiaService.getItineraries(trip);

    // Récupération des sections créés par GTFS
    Set<SectionDTO> gtfsSectionProposals = gtfsService.getSectionsFromGtfsData(trip);

    // Ajout des sections gtfs aux trajets de Navitia
    JourneyProposalsDTO completeJourneyProposals = assembleNavitiaAndGtfsSections(trip, navitiaJourneyProposals, gtfsSectionProposals);

    // On renvoie l'objet entier formaté
    return completeJourneyProposals;
  }

  protected boolean getIfBoatIsFirstSection(TripDTO trip){
    // On vérifie la direction
    if(trip.getDeparture().getName().contains("Quiberon")){
      // Trajet depuis Belle ile
      return true;

    }else if(trip.getArrival().getName().contains("Quiberon")){
      // Trajet qui va à belle ile
      return false;
    }

    System.out.println("Impossible de déterminer la place du bateau dans le trajet");
    return false;
  }

  private JourneyProposalsDTO assembleNavitiaAndGtfsSections(TripDTO trip, JourneyProposalsDTO navitiaJourneyProposals, Set<SectionDTO> gtfsSectionProposals){
    // Trouver le sens du trajet
    boolean isBoatFirstSection = getIfBoatIsFirstSection(trip);

    for(JourneyDTO navitiaJourneyProposal : navitiaJourneyProposals.getJourneyProposals()){
      navitiaJourneyProposal = addGtfsSection(navitiaJourneyProposal, gtfsSectionProposals, isBoatFirstSection);
    }

    return navitiaJourneyProposals;
  }

  private JourneyDTO addGtfsSection(JourneyDTO navitiaJourneyProposal, Set<SectionDTO> gtfSectionProposals, boolean isBoatFirstSection){

    Set<SectionDTO> compatibleBoats = getCompatibleBoats(gtfSectionProposals, navitiaJourneyProposal, isBoatFirstSection);
    SectionDTO mostOptimizedBoat = getMostOptimizedBoat(compatibleBoats, navitiaJourneyProposal, isBoatFirstSection);

    if(isBoatFirstSection){
      if(mostOptimizedBoat.getDepartureDateTime() != null){
        navitiaJourneyProposal.getSections().add(0, mostOptimizedBoat); // <- ajouté ici
      }
    }else{
      if(mostOptimizedBoat.getDepartureDateTime() != null){
        navitiaJourneyProposal.getSections().add(mostOptimizedBoat); // <- ajouté ici
      }
    }

    // Si bateau à la fin, on ajoute à la fin

    return navitiaJourneyProposal;
  }

  protected Set<SectionDTO> getCompatibleBoats(Set<SectionDTO> gtfsSectionProposals , JourneyDTO journeyProposal, boolean isBoatFirstSection){
    Set<SectionDTO> compatibleGtfsSections = new HashSet<SectionDTO>();

    LocalDateTime trainDeparture = journeyProposal.getFirstDeparturDateTime();
    LocalDateTime trainArrival = journeyProposal.getLastArrivalDateTime();

    if(isBoatFirstSection){
      compatibleGtfsSections = gtfsSectionProposals.stream()
                                .filter(s -> {
                                  LocalDateTime boatArrival = LocalDateTime.parse(s.getArrivalDateTime());
                                  long connectionTime = boatArrival.until(trainDeparture, ChronoUnit.SECONDS);
                                  return connectionTime >= MIN_TRANSFERT_TIME_IN_SECONDS;
                                } )
                                .collect(Collectors.toSet());
    }else{
      compatibleGtfsSections = gtfsSectionProposals.stream()
                                .filter(s -> {
                                  LocalDateTime boatDeparture = LocalDateTime.parse(s.getDepartureDateTime());
                                  long connectionTime = trainArrival.until(boatDeparture, ChronoUnit.SECONDS);
                                  return connectionTime >= MIN_TRANSFERT_TIME_IN_SECONDS;
                                } )
                                .collect(Collectors.toSet());
    }

    return compatibleGtfsSections;
  }

  protected SectionDTO getMostOptimizedBoat(Set<SectionDTO> gtfsSectionProposals , JourneyDTO journeyProposal, boolean isBoatFirstSection){

    long minConnectionTime = Long.MAX_VALUE;
    SectionDTO mostOptimizedBoat = new SectionDTO();

    if(isBoatFirstSection){
      for(SectionDTO boatSection : gtfsSectionProposals){
        LocalDateTime boatArrival = LocalDateTime.parse(boatSection.getArrivalDateTime());
        LocalDateTime trainDeparture = journeyProposal.getFirstDeparturDateTime();

        long connectionTime = boatArrival.until(trainDeparture, ChronoUnit.SECONDS);

        if(minConnectionTime >= connectionTime) {
          minConnectionTime = connectionTime;
          mostOptimizedBoat = boatSection;

        }
      }
    }else{
      for(SectionDTO boatSection : gtfsSectionProposals){
        LocalDateTime boatDeparture = LocalDateTime.parse(boatSection.getDepartureDateTime());
        LocalDateTime trainArrival = journeyProposal.getLastArrivalDateTime();

        long connectionTime = trainArrival.until(boatDeparture, ChronoUnit.SECONDS);

        if(minConnectionTime >= connectionTime){
          minConnectionTime = connectionTime;
          mostOptimizedBoat = boatSection;
        }

      }
    }

    return mostOptimizedBoat;

  }
}


      // Pour chaque bateau trouvé
        // Je vérifie que l'heure d'arrivée du bateau est bien avant celle du train + temps
        // Je calcule le temps d'attente
        // Si je trouve un temps d'attente moindre, je le remplace

      // On vérifie que l'heure de départ du bateau est bien avant celle du train (+ du temps pour la correspondance)

      // durations -> Modification de total + walking avec la correspondance

      // sections -> insertion de la section au début de la liste
      // firstDeparturDateTime -> on met à jour avec l'heure de départ du bateau (heure de départ de la section gtfs)
      // journeyFirstSection est remplacée par la section gtfs
      // duration est mis à jour avec la nouvelle durée
      // nb_transfers est incrémenté de 1
