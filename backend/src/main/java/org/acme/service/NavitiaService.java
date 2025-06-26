package org.acme.service;

import org.acme.client.NavitiaClient;
import org.acme.dto.TripDTO;
import org.acme.model.Trip;
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
    String id_departure = trip.getDeparture().getId();
    String id_arrival = trip.getArrival().getId();
    return navitiaClient.getAllItinaries(id_departure, id_arrival);
  }
}
