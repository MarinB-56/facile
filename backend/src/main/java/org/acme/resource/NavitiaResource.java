package org.acme.resource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

import org.acme.client.NavitiaClient;
import org.acme.dto.JourneyProposalsDTO;
import org.acme.dto.StopPointDTO;
import org.acme.dto.TripDTO;
import org.acme.service.BuildJourneyService;
import org.acme.service.NavitiaService;
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
  NavitiaService navitiaService;

  @Inject
  BuildJourneyService buildJourneyService;

  @GET
  @Path("/{query}")
  public String getLocations(@PathParam("query") String query){
    return navitiaService.getAllLocations(query);
  }

  @GET
  @Path("/{query}/{type:.*}")
  public String getLocations(@PathParam("query") String query, @PathParam("type") @DefaultValue("") String type){
    return navitiaService.getAllLocations(query, type);
  }

  @GET
  @Path("/ping")
  public String ping() {
      return "OK";
  }

  @GET
  @Path("/essai")
  public JourneyProposalsDTO searchAndBuildTrip(){
    TripDTO trip = new TripDTO();

    StopPointDTO arrival = new StopPointDTO();
    StopPointDTO departure = new StopPointDTO();

    arrival.setEmbeddedType("stop_area");
    arrival.setId("stop_area:SNCF:87476457");
    arrival.setName("Quiberon (Quiberon)");

    departure.setEmbeddedType("stop_area");
    departure.setId("admin:fr:83137");
    departure.setName("Toulon (83000-83200)");

    LocalDateTime date = LocalDateTime.of(2025, 10, 30, 8, 0, 0);
    System.out.println(date);

    trip.setDate(date);
    trip.setArrival(arrival);
    trip.setDeparture(departure);

    return buildJourneyService.buildJourney(trip);
  }

  @POST
  @Path("/search")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public JourneyProposalsDTO searchTrip(TripDTO trip){
    ZoneId parisZone = ZoneId.of("Europe/Paris");

    // Convertir LocalDateTime reçu comme s'il était UTC en LocalDateTime Paris
    OffsetDateTime odtUtc = trip.getDate().atOffset(ZoneOffset.UTC);
    LocalDateTime localParis = odtUtc.atZoneSameInstant(parisZone).toLocalDateTime();

    trip.setDate(localParis);
    System.out.println("Date du voyage recherché resource : " + trip.getDate());

    return navitiaService.getItineraries(trip);
  }
}
