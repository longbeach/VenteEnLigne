package com.eni.dvtjeb.metier.interceptors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;

import com.eni.dvtejb.metier.entities.Stock;
import com.eni.dvtejb.metier.services.ArticlePanier;

public class CommanderInterceptor implements Serializable  {
	
	private static final Logger log = Logger.getLogger(CommanderInterceptor.class);
	
	@PersistenceContext
	private EntityManager em;
	
	@AroundInvoke  
	public Object diminuerQuantiteStock(InvocationContext ctx) throws Exception  {
		 log.info(" ------------ Debut d'interception  ------------ ");  
		 
		 try {			 
			 ArrayList<ArticlePanier> articlesPanier = (ArrayList<ArticlePanier>)ctx.getParameters()[1];
			
			 ArticlePanier artPanier = new ArticlePanier();
			   	for (Iterator it = articlesPanier.iterator (); it.hasNext (); ) {
			   		
			   		artPanier = (ArticlePanier)it.next ();
					Long stockId = artPanier.getArticle().getStockFK().getStockid();
					Stock stock  = em.find(Stock.class, stockId);
					BigDecimal quantiteActuelle = stock.getQuantite();
					BigDecimal quantiteCommandee = artPanier.getQuantite();
					BigDecimal nouvelleQuantite = quantiteActuelle.subtract(quantiteCommandee);
					
					stock.setQuantite(nouvelleQuantite);
					em.persist(stock);		  
				  } 
			 
			   	log.info(" ------------ Fin d'interception  ------------ ");  
				 return ctx.proceed();  			 
		 }
		  catch(Exception e)  
		        {  
		           throw e;  
		        }  
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
