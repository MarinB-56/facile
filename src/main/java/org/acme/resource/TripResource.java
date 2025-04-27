package org.acme.resource;

import java.util.List;

import org.acme.model.Trip;
import org.acme.repository.TripRepository;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

@Path("/trips")
public class TripResource {
  @Inject
  TripRepository tripRepository;

  // Index (trips)
  // Show (trips/:id)
  // New (trips/new)
    // Create (trips)
  // Edit (trips/:id/edit)
    // Update (trips/:id)
  // Delete (trips/:id)

  @GET
  public List<Trip> getAllTrips(){
    return tripRepository.findAll();
  }

  @GET
  @Path("/{id}")
  public Trip getTripById(@PathParam("id") Long id){
    return tripRepository.findById(id);
  }

  @POST
  @Transactional
  public Response createTrip(Trip trip){
    tripRepository.save(trip);
    return Response.status(Response.Status.CREATED).entity(trip).build();
  }

  @PATCH
  @Transactional
  @Path("/{id}")
  public Response updateTrip(@PathParam("id") Long id, Trip trip){
    Trip existingTrip = tripRepository.findById(id);
    if(existingTrip == null){
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    existingTrip.setArrivalDate(trip.getArrivalDate());
    existingTrip.setArrivalLocation(trip.getArrivalLocation());
    existingTrip.setConnectionsCount(trip.getConnectionsCount());
    existingTrip.setDepartureDate(trip.getDepartureDate());
    existingTrip.setDepartureLocation(trip.getDepartureLocation());
    existingTrip.setSubtrips(trip.getSubtrips());

    tripRepository.update(existingTrip);
    return Response.ok(existingTrip).build();
  }

  @DELETE
  @Transactional
  @Path("/{id}")
  public Response deleteTrip(@PathParam("id") Long id){
    Trip existingTrip = tripRepository.findById(id);

    if(existingTrip == null){
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    tripRepository.deleteById(id);
    return Response.noContent().build();
  }
}
