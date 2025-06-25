package org.acme.resource;

import org.acme.client.NavitiaClient;
import org.acme.dto.TripDTO;
import org.eclipse.microprofile.rest.client.inject.RestClient;

// import jakarta.enterprise.inject.Produces; // Removed, not needed for REST endpoints
import jakarta.ws.rs.Produces;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;

@Path("api/navitia")
public class NavitiaResource {
  @Inject
  @RestClient
  NavitiaClient navitiaClient;

  @GET
  @Path("/{query}")
  public String getLocations(@PathParam("query") String query){
    return navitiaClient.getAllLocations(query);
  }

  @GET
  @Path("/{query}/{type:.*}")
  public String getLocations(@PathParam("query") String query, @PathParam("type") @DefaultValue("") String type){
    return navitiaClient.getAllLocations(query, type);
  }

  @GET
  @Path("/ping")
  public String ping() {
      return "OK";
  }

  @POST
  @Path("/search")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public void searchTrip(TripDTO trip){
    if (trip == null) {
      System.out.println("TripDTO is null");
    } else {
      // Utilisation des données
      System.out.println(trip.toString());
    }
  }
}


// public void searchTrip(DestinationDTO destination){
//    System.out.println(destination.id);
// }
