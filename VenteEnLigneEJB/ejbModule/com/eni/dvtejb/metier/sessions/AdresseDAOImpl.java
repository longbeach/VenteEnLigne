package com.eni.dvtejb.metier.sessions;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.eni.dvtejb.metier.entities.Adresse;

@Stateless
public class AdresseDAOImpl implements AdresseDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	private Logger log = Logger.getLogger(this.getClass());
	
	public void save(Adresse adresse){
		log.debug("Sauve l'adresse :" + adresse);
		em.persist(adresse);
	}

	public void merge(Adresse adresse) {
		em.merge(adresse);
	}
	 
	public List findAll() {
		log.debug("recherche toutes les adresses");
		return em.createQuery("from Adresse").getResultList();
	}

	public Adresse findById(Integer id) {
		return em.find(Adresse.class, id);
	}
	
	public List findByRue(String rue){
		log.debug("recherche par nom");
		return em.createQuery("from Adresse a where a.rue = :rue").setParameter("rue", rue).getResultList();
	}
	
	public List findByVille(String ville){
		log.debug("recherche par ville");
		return em.createQuery("from Adresse a where a.ville = :ville").setParameter("ville", ville).getResultList();
	}
}
