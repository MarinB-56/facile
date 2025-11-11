package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.acme.dto.SectionDTO;
import org.acme.dto.TripDTO;
import org.acme.service.GtfsService;
import org.onebusaway.gtfs.model.StopTime;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Set;

@Path("/gtfs")
public class GtfsResource {
  @Inject
  GtfsService gtfsService;

  @POST
  @Path("/schedule")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Set<SectionDTO> demarrageGTFS(TripDTO trip){
    System.out.println("Arrivée dans l'endpoint gtfs");
    ZoneId parisZone = ZoneId.of("Europe/Paris");

    // Convertir LocalDateTime reçu comme s'il était UTC en LocalDateTime Paris
    OffsetDateTime odtUtc = trip.getDate().atOffset(ZoneOffset.UTC);
    LocalDateTime localParis = odtUtc.atZoneSameInstant(parisZone).toLocalDateTime();

    trip.setDate(localParis);

    return gtfsService.getSectionsFromGtfsData(trip);
  }

  @GET
  @Path("/stop")
  public Collection<StopTime> getStopTimes(){
    return gtfsService.getStopTimes();
  }

}
