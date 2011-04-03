package com.eni.dvtejb.metier.sessions;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.logging.Logger;

import com.eni.dvtejb.metier.entities.Article;
import com.eni.dvtejb.metier.entities.Stock;

@Stateless
public class StockTimerService implements StockTimerServiceRemote {	
	
	private static final Logger log = Logger.getLogger(StockTimerService.class);


	 //Une r�f�rence � une instance de TimerService est inject�e.
	 @Resource
	  javax.ejb.TimerService timerService;
	 
	 @PersistenceContext(unitName="VenteEnLigneModuleEJB")
	 private EntityManager em;
	 
	 private Timer timer = null;
	
	 public static final String SELECT_ARTICLE = "Select a FROM Article a join a.stockFK stock where stock.stockid = ?1";
	 
	 public StockTimerService() {
	  }

	  public void verifierStocks() {	    
	 
	    // Cr�ation d'un timer 
        // Syntaxe : timerService.createTimer(dateDebut en millisecondes, intervalle en millisecondes, instanceSerializable);
	    // Le timer se d�clenchera pour la 1�re fois au bout de 5000 millisecondes, soit 5 secondes, 
		// � partir du moment o� la m�thode est invoqu�e.
		// Puis il partira en timeout toutes les 30000 millisecondes, soit 30 secondes
		timer = timerService.createTimer(5000, 30000, "TimerStocks");
		  
		// La m�thode est surcharg�e :
		// 1) createTimer(long duree, Serializable objet) ==> le timer expire au bout de "duree" secondes,
		// � partir du moment o� la m�thode est appel�e.
		// 2) createTimer(Date uneDate, Serializable objet)	    
	    //timerService.createTimer(new Date(System.currentTimeMillis() + 1000), Serializable objet);
	  	   	  		 
	  }

	  // Cette m�thode est invoqu�e par le container quand l'objet Timer a expir�. 
	  @Timeout
	  public void maintenance(javax.ejb.Timer timer) {
		log.info("------------ M�thode maintenance() : debut --------");
	    String intoTimer = (String) timer.getInfo();
	    log.info(intoTimer);
	    
	    List stocks = em.createQuery("from Stock").getResultList(); 
	    Iterator iter = stocks.iterator();
		   
			while (iter.hasNext()) {
				  Stock stock = (Stock) iter.next();
				  
				  if (stock.getQuantite().intValue() < 5000) {				 
					  
					  Query query = em.createQuery(SELECT_ARTICLE);
					  query.setParameter(1, stock.getStockid());
					  List<Article> resultat = query.getResultList();
					  
					  String nomArticle = null;
					  for (Article a : resultat) {
						  nomArticle = a.getNom();
		    	        }
					  
					  log.info("************************  STOCK EPUISE OU PRESQUE ! ***************************************");
					  log.info("******* Envoi d'email d'avertissement au fournisseur : ") ;
					  log.info("******* Nom de l'article : " + nomArticle);
					  log.info("******* Stockid : " + stock.getStockid());
					  log.info("******* Quantite : "  + stock.getQuantite());
					  log.info("************************  STOCK EPUISE OU PRESQUE ! ***************************************");
				  }								  
			}				
	  }
	   
	  public void arreterTimers() {
		  log.info("------ M�thode arreterTimer() : debut ------");
		  
		  // R�cup�ration des timers associ�s au bean et affichage des informations
		  for (Object obj : timerService.getTimers()) {			  
			  Timer timer = (Timer)obj;
			  
			   log.info("------  Informations sur le timer : ------ " + timer.getInfo());
			   log.info("------  Prochain timeout : ------ " + timer.getNextTimeout());
			   log.info("------  Temps restant : ------ " + timer.getTimeRemaining());
			  
			  if ("TimerStocks".equals((String)timer.getInfo())){
				  log.info(" ------------- Arr�t du timer TimerStocks ------------- " );
				  timer.cancel();
			  }
		  }
		  
		  log.info("------ M�thode arreterTimer() : fin ------");
	  }

}
