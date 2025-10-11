package org.acme.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.service.GtfsService;
import org.onebusaway.gtfs.model.Route;
import org.onebusaway.gtfs.model.Stop;
import org.onebusaway.gtfs.model.StopTime;
import org.onebusaway.gtfs.model.Trip;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Path("/gtfs")
public class GtfsResource {
  @Inject
  GtfsService gtfsService;

  @GET
  @Path("/essai")
  public Collection<Trip> demarrageGTFS(){
    return gtfsService.getTripsFromTo("I56QUI", "I56BIP");
  }

  @GET
  @Path("/stop")
  public Collection<StopTime> getStopTimes(){
    return gtfsService.getStopTimes();
  }

}
