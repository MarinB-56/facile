package org.acme.resource;

import java.util.List;

import org.acme.client.NavitiaClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

// Removed: import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

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
}
