package org.acme.resource;

import java.util.List;

import org.acme.client.NavitiaClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

// Removed: import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("api/navitia")
public class NavitiaResource {
  @Inject
  @RestClient
  NavitiaClient navitiaClient;

  @GET
  @Path("/{query}") //Permet une query vide
  // public String getLocations(@PathParam("query") @DefaultValue("") String query){
  //   return navitiaClient.getAllLocations(query);
  // }
  public String getLocations(@PathParam("query") String query){
    System.out.println(query);
    return navitiaClient.getAllLocations(query);
  }

  @GET
  @Path("/ping")
  public String ping() {
      return "OK";
  }
}
