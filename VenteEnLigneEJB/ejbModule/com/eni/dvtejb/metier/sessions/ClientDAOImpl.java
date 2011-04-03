package com.eni.dvtejb.metier.sessions;

import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.ExcludeClassInterceptors;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.eni.dvtejb.metier.entities.Client;
import com.eni.dvtjeb.metier.interceptors.NouvelUtilisateurInterceptor;

@Interceptors(NouvelUtilisateurInterceptor.class)
@Stateless
public class ClientDAOImpl implements ClientDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	private Logger log = Logger.getLogger(this.getClass());
	
	public void save(Client client){
		log.debug("Enregistrement du client :" + client);
		em.persist(client);
	}

	@ExcludeClassInterceptors 
	public void merge(Client client) {
		em.merge(client);
	}
	 
	@ExcludeClassInterceptors	
	public List findAll() {
		log.info("Recherche de tous les clients");
		return em.createQuery("from Client").getResultList();
	}
	
	@ExcludeClassInterceptors
	public Client findById(Long id) {
		return em.find(Client.class, id);
	}
	
	@ExcludeClassInterceptors
	public List findByNom(String nom){
		log.info("Recherche de clients par nom");
		return em.createQuery("from Client c where c.nom = :nom").setParameter("nom", nom).getResultList();
	}
	
	@ExcludeClassInterceptors
	public List findByPrenom(String prenom){
		log.info("recherche par prenom");
		Query q = em.createNamedQuery("Utilisateur.findByPrenom"); 
		q.setParameter("lePrenom", prenom);
		List<Client> resultatsNamedQuery  = q.getResultList();
 	    return resultatsNamedQuery;
	}
	
}
