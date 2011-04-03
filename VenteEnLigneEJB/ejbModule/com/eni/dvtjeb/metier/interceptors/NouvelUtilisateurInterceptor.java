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
			  * 1. R�cuperer le client en cours de cr�ation : c'est le param�tre de la m�thode.
			  * 2. V�rifier que son email n'existe pas d�j� en base
			  * 3. Annuler l'inscription si l'email existe d�j�, sinon poursuivre l'insertion
			  */
			 
			 Client client = (Client)ctx.getParameters()[0];
			 String email = client.getEmail();
			 log.info("email vaut : " + email);
			 Client doublon = (Client)em.createQuery("select c from Client c where c.email = :email").setParameter("email", email).getSingleResult();
			 
			 if (null != doublon) {
				 log.info(" ----------- Cette adresse email existe d�j� !");
				 throw new AdresseEmailException("Cette adresse email existe d�j� !");		
			 }
		 }
		 catch (NoResultException nre){
			 // La m�thode getSingleResult() lance une exception de type NoResultException si la requ�te ne retourne aucun r�sultat
			 log.info(" ----------- Exception NoResultException: cet email n'existe pas encore en base ------------- ");
		 }
		 catch(Exception e)  {  
		           throw e;  
		 }  
		 return ctx.proceed(); 
	}
		
	@PostActivate
	  public void postActivate(InvocationContext ic) {
		log.info("M�thode appel�e : " + ic.getMethod());
	  }

	  @PrePassivate
	  public void prePassivate(InvocationContext ic) {
		log.info("M�thode appel�e : " + ic.getMethod());
	  }

	  @PreDestroy
	  public void preDestroy(InvocationContext ic) {
		  log.info("Le bean intercept� : " + ic.getTarget() + " va �tre supprim�.");
	  }
	    
	  @PostConstruct
	   public void postConstruct(InvocationContext ic) {
		  log.info("Le bean intercept� : " + ic.getTarget() + " a �t� cr�e.");
	   }
}
