package org.acme.service;

import java.util.List;
import java.util.Set;

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

    if(trip.getDeparture().getName().contains("Quiberon")){
      // Trajet depuis Belle ile
      isBoatFirstSection = true;

    }else if(trip.getArrival().getName().contains("Quiberon")){
      // Trajet qui va à belle ile
      isBoatFirstSection = false;
    }
    System.out.println("COUI");
    // Assemblage des trips

    System.out.println(navitiaJourneyProposals.getJourneyProposals().size());

    for(JourneyDTO navitiaJourneyProposal : navitiaJourneyProposals.getJourneyProposals()){
      System.out.println("Oui monsieur");
      navitiaJourneyProposal = addGtfsSection(navitiaJourneyProposal, gtfsSectionProposals, isBoatFirstSection);
    }

    return new JourneyProposalsDTO();
  }

  private JourneyDTO addGtfsSection(JourneyDTO navitiaJourneyProposal, Set<SectionDTO> gtfSectionProposals, boolean isBoatFirstSection){
    System.out.println("Couou");
    System.out.println(navitiaJourneyProposal.getJourneyFirstSection().getDepartureDateTime());

    return new JourneyDTO();
  }
}
