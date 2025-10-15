package org.acme.service;

import java.util.Set;

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
    // Récupération des trajets créés par Navitia
    JourneyProposalsDTO navitiaJourneyProposals = navitiaService.getItineraries(trip);

    // Récupération des sections créés par GTFS
    Set<SectionDTO> gtfsSectionProposals = gtfsService.getSectionsFromGtfsData(trip);

    // Ajout des sections gtfs aux trajets de Navitia
    JourneyProposalsDTO completeJourneyProposals = assembleNavitiaAndGtfsSections(trip, navitiaJourneyProposals, gtfsSectionProposals);

    System.out.println("Nombre de voyages : " + completeJourneyProposals.getJourneyProposals().size());

    // On renvoie l'objet entier formaté
    return completeJourneyProposals;
  }

  private JourneyProposalsDTO assembleNavitiaAndGtfsSections(TripDTO trip, JourneyProposalsDTO navitiaJourneyProposals, Set<SectionDTO> gtfsSectionProposals){
    // Trouver le sens du trajet
    boolean isBoatFirstSection = false;
    // On vérifie la direction
    if(trip.getDeparture().getName().contains("Quiberon")){
      // Trajet depuis Belle ile
      isBoatFirstSection = true;

    }else if(trip.getArrival().getName().contains("Quiberon")){
      // Trajet qui va à belle ile
      isBoatFirstSection = false;
    }

    /*
     * TODO : Gérer le cas où aucun voyage en train n'est trouvé
     */

    for(JourneyDTO navitiaJourneyProposal : navitiaJourneyProposals.getJourneyProposals()){
      navitiaJourneyProposal = addGtfsSection(navitiaJourneyProposal, gtfsSectionProposals, isBoatFirstSection);
      System.out.println(navitiaJourneyProposal.toString());
    }

    return navitiaJourneyProposals;
  }

  private JourneyDTO addGtfsSection(JourneyDTO navitiaJourneyProposal, Set<SectionDTO> gtfSectionProposals, boolean isBoatFirstSection){


    long minTimeToWaitBetweenBoatAndTrainSections = Long.MAX_VALUE;
    SectionDTO mostOptimizedBoatSection = new SectionDTO();

    // System.out.println("Recherche du bateau le plus optimisé. Arrivée à quiberon : "
    //             + navitiaJourneyProposal.getJourneyLastSection().getArrivalDateTime());

    // On vérifie si le trajet est compatible ou non
      // Pour ça, on fait une liste avec chaque bateau compatible avec le trajet
      // On garde celui qui est le plus arrangeant (on compare l'heure d'arrivée du bateau, on ajoute 30 min de marche, on regarde l'heure de départ du train)

    // Si bateau à la fin, on ajoute à la fin
    if(!isBoatFirstSection){
      LocalDateTime journeyArrivalDateTime = navitiaJourneyProposal.getLastArrivalDateTime();
      for(SectionDTO boatSection : gtfSectionProposals){

        LocalDateTime boatSectionDepartureDateTime = LocalDateTime.parse(boatSection.getDepartureDateTime(), formatter);;

        Long timeToWaitBeforeBoat = journeyArrivalDateTime.until(boatSectionDepartureDateTime, ChronoUnit.SECONDS);

        // heure d'arrivée until heure de départ du bateau 1800 secondes nécessaires à la traversée
        // si la durée est au moins aussi grande que 1800, alors on peut considérer que c'est compatible
        // On enregistre la durée d'attente

        if(timeToWaitBeforeBoat >= MIN_TRANSFERT_TIME_IN_SECONDS){
          // compatible
          //System.out.println("Temps d'attente : " + timeToWaitBeforeBoat);

          if(minTimeToWaitBetweenBoatAndTrainSections > timeToWaitBeforeBoat){
            //System.out.println("Trajet le plus court mis à jour");
            minTimeToWaitBetweenBoatAndTrainSections = timeToWaitBeforeBoat;
            mostOptimizedBoatSection = boatSection;
          }
        }
      }

      // Si un trajet a été trouvé, on l'ajoute au voyage Journey
      if(mostOptimizedBoatSection.getDepartureDateTime() == null){
        System.out.println("Aucun bateau compatible");
      }else{
        navitiaJourneyProposal.getSections().add(mostOptimizedBoatSection);
      }

      // System.out.println("après: " + navitiaJourneyProposal.getSections().size());
    } else if(isBoatFirstSection){
      LocalDateTime journeyDepartureDateTime = navitiaJourneyProposal.getFirstDeparturDateTime();

      for(SectionDTO boatSection : gtfSectionProposals){

        LocalDateTime boatSectionArrivalDateTime = LocalDateTime.parse(boatSection.getArrivalDateTime(), formatter);;

        Long timeToWaitAfterBoat = boatSectionArrivalDateTime.until(journeyDepartureDateTime, ChronoUnit.SECONDS);

        // heure d'arrivée until heure de départ du bateau 1800 secondes nécessaires à la traversée
        // si la durée est au moins aussi grande que 1800, alors on peut considérer que c'est compatible
        // On enregistre la durée d'attente

        if(timeToWaitAfterBoat >= MIN_TRANSFERT_TIME_IN_SECONDS){
          // compatible
          //System.out.println("Temps d'attente : " + timeToWaitBeforeBoat);

          if(minTimeToWaitBetweenBoatAndTrainSections > timeToWaitAfterBoat){
            //System.out.println("Trajet le plus court mis à jour");
            minTimeToWaitBetweenBoatAndTrainSections = timeToWaitAfterBoat;
            mostOptimizedBoatSection = boatSection;
          }
        }
      }

      // Si un trajet a été trouvé, on l'ajoute au voyage Journey
      if(mostOptimizedBoatSection.getDepartureDateTime() == null){
        System.out.println("Aucun bateau compatible");
      }else{
        // ajout du bateau au voyage (au début du voyage) // Pas très optimisé
        /*
         * TODO : optimiser l'insertion dans la liste (changer en LinkedList ?)
         */
        navitiaJourneyProposal.getSections().add(0, mostOptimizedBoatSection);
      }
    }

    return navitiaJourneyProposal;
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
