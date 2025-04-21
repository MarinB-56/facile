package org.acme.repository;

import java.util.List;

import org.acme.model.Trip;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class TripRepository {
  @PersistenceContext
  EntityManager em;

  // New - Insert
  public Trip save(Trip trip){
    em.persist(trip);
    return trip;
  }

  // findById
  public Trip findById(Long id){
    return em.find(Trip.class, id);
  }

  // findAll
  public List<Trip> findAll(){
    return em.createQuery("FROM Trip", Trip.class).getResultList();

  }

  // delete
  public void delete(Trip trip){
    em.remove( em.contains(trip) ? trip : em.merge(trip));
  }

  // deleteById
  public void deleteById(Long id){
    Trip trip = findById(id);
    if(trip != null ){
      em.remove(trip);
    }
  }
}
