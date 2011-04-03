package com.eni.dvtejb.clientStruts2.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;

import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.SessionAware;
import org.jboss.logging.Logger;

import com.eni.dvtejb.metier.entities.Article;
import com.eni.dvtejb.metier.entities.Client;
import com.eni.dvtejb.metier.entities.Commande;
import com.eni.dvtejb.metier.entities.Utilisateur;
import com.eni.dvtejb.metier.services.ArticlePanier;
import com.eni.dvtejb.metier.services.ServiceLocator;
import com.eni.dvtejb.metier.sessions.FacadeRemote;
import com.eni.dvtejb.metier.sessions.PanierBeanRemote;
import com.opensymphony.xwork2.ActionSupport;

public class MagasinAction extends ActionSupport implements SessionAware, ParameterAware  {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(MagasinAction.class);
	
	private Map session ;
	private Map parameters;

	public Map getParameters() {
		return parameters;
	}

	public void setParameters(Map parameters) {
		this.parameters = parameters;
	}

	public void setSession(Map map) {
		this.session = map;	
	}
	
	private BigDecimal quantite;
	
	public BigDecimal getQuantite() {
		return quantite;
	}

	public void setQuantite(BigDecimal quantite) {
		this.quantite = quantite;
	}

	private List catalNom;
	private List prodNom;
	private List artNom;
	private List articleNumero;
	private List articlePrix;
	private List stockQtite;
	 
	// Ecran Commandes
	private List commandeId;
	private List articleNom;
	private List dateCommande;
	private String numCC;
	private String typeCC;
	 
	public String getNumCC() {
			return numCC;
	}

	public String getTypeCC() {
			return typeCC;
	}

	public List getStockQtite() {
		return stockQtite;
	}

	public List getCatalNom(){
		    return catalNom;
	 }
	 
	 public List getProdNom(){
		    return prodNom;
	 }
	 
	 public List getArtNom(){
		    return artNom;
	 }
	 
	 public List getArticleNumero(){
		    return articleNumero;
	 }
	 
	 public List getArticlePrix(){
		    return articlePrix;
	 }
	 
	 // Commandes
	 public List getCommandeId(){
		    return commandeId;
	 }
	 
	 public List getArticleNom(){
		    return articleNom;
	 }
	 
	 public List getDateCommande(){
		    return dateCommande;
	 }

	public String execute() throws Exception {
			
		InitialContext initialContext = new InitialContext(); 
		
		FacadeRemote facade = (FacadeRemote) ServiceLocator.getInstance().getService("VenteEnLigne/FacadeBean/remote");
    	
    	// Renvoie une liste de Object[]
    	// Le 1er élément est une instance de Catalogue, le 2nd élément est une instance de Produit,
    	// le 3ème élément est une instance d'Article, etc 
    	// {(CatalogueA, ProduitA, ArticleA), (CatalogueB, ProduitB, ArticleB), (CatalogueC, ProduitC, ArticleC), ...}
    	List magasin = facade.findMagasin();
    	/*
    	Object[] catalogue = (Object[])magasin.get(0); // La 1ère ligne
    	Object[] product = (Object[])magasin.get(1);
    	Object[] article = (Object[])magasin.get(2);
    	*/    	
    	int taille  = magasin.size();
    	
    	// Ces ArrayList sont automatiquement placées dans la stack OGNL
    	catalNom = new ArrayList(); 
    	prodNom = new ArrayList();
    	artNom = new ArrayList();
    	articleNumero = new ArrayList();
    	articlePrix = new ArrayList();
    	stockQtite = new ArrayList();
    	    	
       for (int i = 0; i<taille;i++){
    	   Object[] ligne = (Object[])magasin.get(i);
    	   
    	   // ligne i = (Cataloguei, Produiti, Articlei, ArticleNumeroi, articlePrixi, stockQtitei) 			
        	String nomCat = (String)ligne[0]; // Cataloguei
        	catalNom.add(nomCat);  
        	String nomProd = (String)ligne[1]; // Produiti
        	prodNom.add(nomProd);  
        	String nomArt = (String)ligne[2]; // Articlei
        	artNom.add(nomArt);  
        	long articleid = (Long)ligne[3];
        	articleNumero.add(articleid);	 
        	double prix = (Double)ligne[4];
        	articlePrix.add(prix);	
        	BigDecimal qtite = (BigDecimal)ligne[5];
        	stockQtite.add(qtite);
       }
		return SUCCESS;
	}

	public String getParameterValue(String param) {
		Object varr = getParameters().get(param);
		if (varr == null) return null;
		return ((String[]) varr)[0];
	}	 
	
	// Ajoute un article au caddie
	public String ajouterArticle() throws Exception {		
		log.info("Appel de la methode ajouterArticle()");
        
    	PanierBeanRemote panierBeanRemote = (PanierBeanRemote) ServiceLocator.getInstance().getService("VenteEnLigne/PanierBean/remote");
    	
    	ArrayList<ArticlePanier> articlesPanier;
    	double montantTotal;
    		
    	Article article = (Article)session.get("monArticle");
       
    	if (null != article) {
    		
    		BigDecimal laQuantite = getQuantite();
    		log.info("La laQuantite est : " + laQuantite);        	
        	panierBeanRemote.ajouterArticle(article, laQuantite);        	
        	articlesPanier = panierBeanRemote.getPanier();
        	session.put("mesArticles", articlesPanier );        	
        	montantTotal = panierBeanRemote.getMontantTotal();
        	session.put("montantTotal", montantTotal );        	
        	log.info("La taille de la liste d'articles est : " + articlesPanier.size());
        	log.info("Article ajouté !");	
    	}
      return SUCCESS;
    }
	
	// Affiche le panier
	public String afficherPanier() throws Exception {		
		log.info("Appel de la methode afficherPanier()");
        
        PanierBeanRemote panierBeanRemote = (PanierBeanRemote) ServiceLocator.getInstance().getService("VenteEnLigne/PanierBean/remote");
    	ArrayList<ArticlePanier> articles;
    	double montantTotal;
    	
    	articles = panierBeanRemote.getPanier();
    	session.put("mesArticles", articles );
    	
    	montantTotal = panierBeanRemote.getMontantTotal();
    	session.put("montantTotal", montantTotal );
        
        return SUCCESS;
	}
	
    // Visualise un article (pour achat). Conduit à la JSP ajout.jsp 
	public String visualiser() throws Exception {
		log.info("Appel de la methode visualiser()");
        
    	InitialContext initialContext = new InitialContext(); 
    	PanierBeanRemote panierBeanRemote = (PanierBeanRemote) initialContext.lookup("VenteEnLigne/PanierBean/remote"); 
    	
    	String numeroId = (String)getParameterValue("numeroId");
   	    long l = Long.parseLong(numeroId);
    	
    	Article monArticle = panierBeanRemote.findById(l);
    	
    	if (null != monArticle){
    		String nomArticle = monArticle.getNom();
    		log.info("Le nom de l'article est :" + nomArticle);
    		session.put("nomArt", nomArticle); 
    		session.put("monArticle", monArticle );
    	} else {
    		log.info("ARTICLE NUL");
    	}    	    	
      return SUCCESS;
    }
	
	public String viderPanier() throws Exception {		
		log.info("Appel de la methode viderPanier()");
		 
		PanierBeanRemote panierBeanRemote = (PanierBeanRemote) ServiceLocator.getInstance().getService("VenteEnLigne/PanierBean/remote");
		panierBeanRemote.viderPanier();
		
		session.remove("mesArticles");
		
    	ArrayList<ArticlePanier> articles;
    	articles = panierBeanRemote.getPanier();

    	log.info("La taille de la liste d'articles après vidage est : " + articles.size());
		return SUCCESS;
	}
	
	// Supprime un article du caddie
	public String supprimerArticle() throws Exception {		
		log.info("Appel de la methode supprimerArticle()");
        	
    	PanierBeanRemote panierBeanRemote = (PanierBeanRemote) ServiceLocator.getInstance().getService("VenteEnLigne/PanierBean/remote");
    	
    	ArrayList<ArticlePanier> articlesPanier;
    	double montantTotal;
    	  	
    	String  articleId =  (String)getParameterValue("articleid");

   	    long artId = Long.parseLong(articleId);
    	Article monArticle = panierBeanRemote.findById(artId);
       
    	if (null != monArticle) { 
   
    		articlesPanier = panierBeanRemote.getPanier();
    		log.info("1 La taille de la liste d'articles avant suppression est : " + articlesPanier.size());
    		
    		ArticlePanier art = new ArticlePanier();
    		for (Iterator it = articlesPanier.iterator (); it.hasNext (); ) {
    			 art = (ArticlePanier)it.next ();
    			 log.info("ezezezeze : " + art.getArticle().getArticleid());
    			if (art.getArticle().getArticleid() == artId){
    				break;
    			}
    		  }
    		    	
    		 panierBeanRemote.supprimerArticle(art);    		    		
    		
    		log.info("2 La taille de la liste d'articles après suppression est : " + articlesPanier.size());
    		articlesPanier = panierBeanRemote.getPanier();
    		log.info("3 La taille de la liste d'articles après getPanier est : " + articlesPanier.size());
        	
        	// Mise à jour de l'arrayList qui est mise en session
        	session.put("mesArticles", articlesPanier );        	
        	montantTotal = panierBeanRemote.getMontantTotal();
        	session.put("montantTotal", montantTotal );        	        	      
        	log.info("Article supprimé !");
    	}    	
      return SUCCESS;
    }
	
	// Le check out de la commande
	public String commander() throws Exception {
		
		Client cl = (Client)session.get("leclient");
		
	    PanierBeanRemote panierBeanRemote = (PanierBeanRemote) ServiceLocator.getInstance().getService("VenteEnLigne/PanierBean/remote");
	    Commande commande = new Commande();
	    
	    ArrayList<ArticlePanier> articlesPanier;
		articlesPanier = panierBeanRemote.getPanier();
	   	
		Calendar calendar = new GregorianCalendar(2001, 3, 11);
		java.sql.Date expirationDate = new java.sql.Date(calendar.getTimeInMillis());
		panierBeanRemote.commander(cl, articlesPanier, numCC, typeCC, expirationDate );
		
        return SUCCESS;
	}
	
	public String afficherHistoCommandes() throws Exception {
		log.info("Appel de la methode afficherHistoCommandes()");
		
		List commandes;
		PanierBeanRemote panierBeanRemote = (PanierBeanRemote) ServiceLocator.getInstance().getService("VenteEnLigne/PanierBean/remote");
    			
		if (null != session) {
			log.info("session non null");
		} else {
			log.info("session  null");
		}
		
    	Utilisateur u = (Utilisateur)session.get("leclient");
    	
    	if (null != u){
    		log.info("Utilisateur non null");
    	} else {
    		log.info("Utilisateur null");
    	}
		
    	commandes = panierBeanRemote.afficherHistoCommandes(u);			
    	int taille  = commandes.size();
    	
    	// Ces ArrayList sont automatiquement placées dans la stack OGNL
    	commandeId = new ArrayList(); 
    	articleNom = new ArrayList();
    	dateCommande = new ArrayList();
       	
       for (int i = 0; i<taille;i++){
    	    Object[] ligne = (Object[])commandes.get(i);
    	   		
    	    BigDecimal commandeIdent = (BigDecimal)ligne[0]; 
        	commandeId.add(commandeIdent);  
        	String artNom = (String)ligne[1];
        	articleNom.add(artNom);  
        	Date dateComm = (Date)ligne[2]; 
        	dateCommande.add(dateComm);  
       }
				
       log.info("Sortie de afficherHistoCommandes()");
	   return SUCCESS;
	}
		
}
