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

  // findAll
  public List<Trip> findAll(){
    return em.createQuery("FROM Trip", Trip.class).getResultList();
  }

  // findById
  public Trip findById(Long id){
    return em.find(Trip.class, id);
  }

  // New - Insert
  public Trip save(Trip trip){
    em.persist(trip);
    return trip;
  }

  public Trip update(Trip trip){
    return em.merge(trip);
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
