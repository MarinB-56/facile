package org.acme.service;

import java.time.LocalDateTime;

import org.acme.client.NavitiaClient;
import org.acme.dto.TripDTO;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class NavitiaService {
  @Inject
  @RestClient
  NavitiaClient navitiaClient;

  // Autocomplete
  public String getAllLocations(String query){
    return navitiaClient.getAllLocations(query);
  }

  // Autocomplete
  public String getAllLocations(String query, String type){
    return navitiaClient.getAllLocations(query, type);
  }

  // Itinary research
  public String getItinaries(TripDTO trip){
    System.out.println(trip.toString());
    // Travailler le truc pour ne pas juste avoir des résultats merdiques !
    String idDeparture = trip.getDeparture().getId();
    String idArrival = trip.getArrival().getId();
    String tripDate = trip.getDate().toString();

    // System.out.println(tripDate.toString());

    return navitiaClient.getAllItinaries(idDeparture, idArrival, tripDate );
  }
}
