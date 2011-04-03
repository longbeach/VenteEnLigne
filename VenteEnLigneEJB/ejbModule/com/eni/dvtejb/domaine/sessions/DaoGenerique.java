package com.eni.dvtejb.domaine.sessions;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Ce DAO session bean générique réside dans la couche Domaine
 */
@Stateless
public class DaoGenerique implements DaoGeneriqueLocal {
	
	@PersistenceContext
	EntityManager em;
	 
	public <T> T ajouter(T t) {
	    em.persist(t);
	    em.flush();
	    em.refresh(t);
	    return t;
	}

	public void supprimer(Object t) {
		   em.merge(t);
		   em.remove(t);
	}
	
	public <T> T chercher(Class<T> type, Object id) {
	   return (T) em.find(type, id);
	}

	public <T> T updater(T t) {
	    return (T)em.merge(t);
	}

	 public void flush() {
		 em.flush();
	 }    
}
