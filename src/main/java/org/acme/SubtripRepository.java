package org.acme;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class SubtripRepository {
  @PersistenceContext
  EntityManager em;

  public Subtrip save(Subtrip subtrip){
    em.persist(subtrip);
    return subtrip;
  }

  public Subtrip findById(Long id){
    return em.find(Subtrip.class, id);
  }

  public List<Subtrip> findAll(){
    return em.createQuery("FROM Subtrip", Subtrip.class).getResultList();
  }

  public void delete(Subtrip subtrip){
    if(em.contains(subtrip)){
      em.remove(subtrip);
    }else {
      em.remove(em.merge(subtrip));
    }
  }

  public void deleteById(Long id){
    Subtrip subtrip = findById(id);
    if(subtrip != null){
      delete(subtrip);
    }
  }

  public Subtrip update(Subtrip subtrip){
    return em.merge(subtrip);
  }
}
