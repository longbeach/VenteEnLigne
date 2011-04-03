package com.eni.dvtejb.metier.sessions;

import java.io.Serializable;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Remote;
import javax.ejb.Remove;
import javax.ejb.Stateful;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.CacheConfig;

@Stateful
@Remote(PanierCycle.class)
@CacheConfig(removalTimeoutSeconds=50, idleTimeoutSeconds=10 )
public class PanierCycleBean implements PanierCycle, Serializable {
	 
	private static final long serialVersionUID = 1L;	
	private static final Logger log = Logger.getLogger(PanierCycleBean.class);
		
	private HashMap<String, Integer> panier = new HashMap<String, Integer>();

   public void acheter(String article, int quantite)
   {
      if (panier.containsKey(article))
      {
         int quantiteCourante = panier.get(article);
         quantiteCourante += quantite;
         panier.put(article, quantiteCourante);
      }
      else
      {
    	  panier.put(article, quantite);
      }
   }

   public HashMap<String, Integer> getContenuPanier()
   {
      return panier;
   }

   @Remove
   public void payer()
   {
      log.info("----> Méthode payer()");
   }
   
   @PostConstruct 
   public void postConstructExemple(){
	   log.info("----> Dans la méthode postConstructExemple");
   }
   
   @PrePassivate
   public void prePassivateExemple(){
	   log.info("----> Dans la méthode prePassivateExemple");
   }
   
   @PostActivate
   public void postActivateExemple(){
	   log.info("----> Dans la méthode postActivateExemple");
   }
   
   @PreDestroy 
   public void preDestroyExemple(){
	   log.info("----> Dans la méthode preDestroyExemple");
   }
}
