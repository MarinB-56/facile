package org.acme.client;

import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;

@RegisterRestClient(configKey = "navitia-api")
public interface NavitiaClient {

  @GET
  @Path("/sncf/places")
  @ClientHeaderParam(name = "Authorization", value = "287e5a3b-64ec-412c-b910-7ae2efe4cb2b")
  String getAllLocations(@QueryParam("q") String query);

  // Méthode surchargée pour accepter le type (stop_area) si besoin
  @GET
  @Path("/sncf/places")
  @ClientHeaderParam(name = "Authorization", value = "287e5a3b-64ec-412c-b910-7ae2efe4cb2b")
  String getAllLocations(@QueryParam("q") String query, @QueryParam("type[]") String type);
}
