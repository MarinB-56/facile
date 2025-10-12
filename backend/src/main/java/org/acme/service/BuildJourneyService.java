package org.acme.service;

import java.util.List;
import java.util.Set;

import org.acme.dto.DurationDTO;
import org.acme.dto.JourneyDTO;
import org.acme.dto.JourneyProposalsDTO;
import org.acme.dto.TripDTO;
import org.acme.dto.SectionDTO;
import java.time.LocalDateTime;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class BuildJourneyService {
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

    // On renvoie l'objet entier formaté
    return navitiaJourneyProposals;
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
    }

    return new JourneyProposalsDTO();
  }

  private JourneyDTO addGtfsSection(JourneyDTO navitiaJourneyProposal, Set<SectionDTO> gtfSectionProposals, boolean isBoatFirstSection){
    System.out.println("Adding gtfs section to journey proposal");

    // On vérifie si le trajet est compatible ou non
      // Pour ça, on fait une liste avec chaque bateau compatible avec le trajet
      // On garde celui qui est le plus arrangeant (on compare l'heure d'arrivée du bateau, on ajoute 30 min de marche, on regarde l'heure de départ du train)

    // Si c'est d'abord le bateau, on ajoute au début
    if(isBoatFirstSection){

      // durations -> Modification de total + walking avec la correspondance

      // sections -> insertion de la section au début de la liste
      // firstDeparturDateTime -> on met à jour avec l'heure de départ du bateau (heure de départ de la section gtfs)
      // journeyFirstSection est remplacée par la section gtfs
      // duration est mis à jour avec la nouvelle durée
      // nb_transfers est incrémenté de 1

    }

    return new JourneyDTO();
  }
}
