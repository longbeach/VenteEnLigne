package com.eni.dvtjeb.metier.interceptors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import com.eni.dvtejb.metier.entities.Client;

public class NouvelUtilisateurInterceptor {
  
	private static final Logger log = Logger.getLogger(NouvelUtilisateurInterceptor.class);
	
	@PersistenceContext
	private EntityManager em;
	
	@AroundInvoke  
	public Object controleEmail(InvocationContext ctx) throws Exception  {
		 log.info(" ------------ Debut d'interception  ------------ ");  
		 
		 try {			 
			 /*
			  * 1. Récuperer le client en cours de création : c'est le paramètre de la méthode.
			  * 2. Vérifier que son email n'existe pas déjà en base
			  * 3. Annuler l'inscription si l'email existe déjà, sinon poursuivre l'insertion
			  */
			 
			 Client client = (Client)ctx.getParameters()[0];
			 String email = client.getEmail();
			 log.info("email vaut : " + email);
			 Client doublon = (Client)em.createQuery("select c from Client c where c.email = :email").setParameter("email", email).getSingleResult();
			 
			 if (null != doublon) {
				 log.info(" ----------- Cette adresse email existe déjà !");
				 throw new AdresseEmailException("Cette adresse email existe déjà !");		
			 }
		 }
		 catch (NoResultException nre){
			 // La méthode getSingleResult() lance une exception de type NoResultException si la requête ne retourne aucun résultat
			 log.info(" ----------- Exception NoResultException: cet email n'existe pas encore en base ------------- ");
		 }
		 catch(Exception e)  {  
		           throw e;  
		 }  
		 return ctx.proceed(); 
	}
		
	@PostActivate
	  public void postActivate(InvocationContext ic) {
		log.info("Méthode appelée : " + ic.getMethod());
	  }

	  @PrePassivate
	  public void prePassivate(InvocationContext ic) {
		log.info("Méthode appelée : " + ic.getMethod());
	  }

	  @PreDestroy
	  public void preDestroy(InvocationContext ic) {
		  log.info("Le bean intercepté : " + ic.getTarget() + " va être supprimé.");
	  }
	    
	  @PostConstruct
	   public void postConstruct(InvocationContext ic) {
		  log.info("Le bean intercepté : " + ic.getTarget() + " a été crée.");
	   }
}
