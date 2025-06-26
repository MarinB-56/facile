package org.acme.service;

import org.acme.client.NavitiaClient;
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

}
