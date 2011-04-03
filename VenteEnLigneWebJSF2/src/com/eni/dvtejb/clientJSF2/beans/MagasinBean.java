package com.eni.dvtejb.clientJSF2.beans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.eni.dvtejb.clientJSF2.services.Pagination;
import com.eni.dvtejb.clientJSF2.services.Services;
import com.eni.dvtejb.metier.dtos.CommandeDTO;
import com.eni.dvtejb.metier.dtos.MagasinDTO;
import com.eni.dvtejb.metier.entities.Article;
import com.eni.dvtejb.metier.entities.Client;
import com.eni.dvtejb.metier.entities.Utilisateur;
import com.eni.dvtejb.metier.services.ArticlePanier;
import com.eni.dvtejb.metier.sessions.FacadeRemote;
import com.eni.dvtejb.metier.sessions.PanierBeanRemote;

@ManagedBean
@SessionScoped
public class MagasinBean {

	private static final Logger log = Logger.getLogger(MagasinBean.class);
	
	// constructeur publique sans argument
	public MagasinBean() {	
		System.out.println("Appel du constructeur");
		pagination = new Pagination();
	}
	
	private Article article;
	List<MagasinDTO> articlesIntervalles;
	List<CommandeDTO> commandes;
	private ArrayList <ArticlePanier> articlesPanier;
	private double montantTotal;
	private Pagination pagination = null;
	private BigDecimal quantite;
	private String detailsArticleId;  // ID de l'article dont on souhaite voir les détails 
	
    public String getDetailsArticleId() {
		return detailsArticleId;
	}

	public void setDetailsArticleId(String detailsArticleId) {
		this.detailsArticleId = detailsArticleId;
	}

	public List<CommandeDTO> getCommandes() {
		return commandes;
	}

    // Renvoie un objet Pagination. Set le nombre total d'articles.
    public Pagination getPagination() {
        	pagination.setNombreArticles(getNombreTotalArticles());
        return pagination;
    }

    public int getNombreTotalArticles() {
        return facade.getNombreTotalArticles();
    }
	
	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public BigDecimal getQuantite() {
		return quantite;
	}

	public void setQuantite(BigDecimal quantite) {
		this.quantite = quantite;
	}
	
	public ArrayList<ArticlePanier> getArticlesPanier() {
		return articlesPanier;
	}

	public void setArticlesPanier(ArrayList<ArticlePanier> articlesPanier) {
		this.articlesPanier = articlesPanier;
	}
		
	//************* Debut DataModel   *****************
	DataModel<ArticlePanier> listeArticlesModel  = new ListDataModel<ArticlePanier>();
	
	public DataModel<ArticlePanier> getListeArticlesModel() {
		// Alimentation du DataModel à partir d'une liste
		listeArticlesModel.setWrappedData(getArticlesPanier());
		return listeArticlesModel;
	}

	public void setListeArticlesModel( DataModel<ArticlePanier> dataModel ) {
		this.listeArticlesModel = dataModel;
	}
	//*************Fin DataModel  *****************
	
	//************* Debut DataModel pour liste des articles *****************
	DataModel<MagasinDTO>  listeArticlesMagasinModel  = new ListDataModel<MagasinDTO>();
	
	public DataModel<MagasinDTO> getListeArticlesMagasinModel() {
		// Alimentation du DataModel à partir d'une liste
		listeArticlesMagasinModel.setWrappedData(getArticlesIntervalles());
		return listeArticlesMagasinModel;
	}

	public void setListeArticlesMagasinModel( DataModel<MagasinDTO> listeArticlesMagasinModel ) {
		this.listeArticlesMagasinModel = listeArticlesMagasinModel;
	}
	//*************Fin DataModel pour liste des articles *****************
	
	public double getMontantTotal() {
		return montantTotal;
	}

	public void setMontantTotal(double montantTotal) {
		this.montantTotal = montantTotal;
	}
	
	@EJB(name="VenteEnLigne/FacadeBean/remote")
	private  FacadeRemote facade;
	
	@EJB(name="VenteEnLigne/PanierBean/remote")
	private  PanierBeanRemote panierBean;
	
	// Injection d'une instance de la classe Services
	@ManagedProperty(value = "#{ServicesVenteEnLigne}")
	private Services services;
	
	// Setter obligatoire
	public void setServices(Services services) {
		this.services = services;
	}
	
	public List<MagasinDTO> getArticlesIntervalles() {
		log.info("Appel de la methode getArticlesIntervalles()");
		
		if (articlesIntervalles == null) {
            getPagination();
            articlesIntervalles = getArticlesSuivants(pagination.getNombreLignes(), pagination.getPremierArticle());
        }
        return articlesIntervalles;		
	}
		
	public String afficherDetails(PhaseEvent event) {
		log.info("Appel de la methode afficherDetails()");
							
		Article monArticle = services.rechercheDetails(getDetailsArticleId());
    	
    	if (null != monArticle){
    		String nomArticle = monArticle.getNom();
    		log.info("Le nom de l'article est :" + nomArticle);
    		article = monArticle;    		
    	} else {
    		log.info("ARTICLE NUL");
    	}
    	return "/magasin/afficherDetails";
	}
	
	// Ajoute un article au caddie/panier
	public String ajouterArticle() throws Exception {		
		log.info("Appel de la methode ajouterArticle()");
    		
    	Article articleNouveau = article;
       
    	if (null != articleNouveau) {
    		
    		BigDecimal laQuantite = getQuantite();
    		log.info("La laQuantite est : " + laQuantite);
        	        	
        	panierBean.ajouterArticle(articleNouveau, laQuantite);
        	
        	articlesPanier = panierBean.getPanier();
        	montantTotal = panierBean.getMontantTotal();
        	log.info("La taille de la liste d'articles est : " + articlesPanier.size());
        	log.info("Article ajouté !");		
    	}
      return "panier";
    }
	
	 // Retourne "max" lignes, à partir de la ligne numéro "premier"
	 public List<MagasinDTO> getArticlesSuivants(int max, int premier) {
	        return facade.findBoutiqueIntervalle(max, premier);
	 }
	
	public String afficherProchainsArticles() {
		    article = null;
	        articlesIntervalles = null;
	        getPagination().pageSuivante();
	        return "listeArticles";
	 }
	 
	 public String afficherPrecedentsArticles() {
		    article = null;
	        articlesIntervalles = null;
	        getPagination().pagePrecedente();
	        return "listeArticles";
	 }
	 
	 
		// Supprime un article du panier
	 public String supprimerArticle() {
     log.info("Appel de la methode supprimerArticle()");

	 long  articleId =  article.getArticleid();
     Article monArticle = panierBean.findById(articleId);
       
    	if (null != monArticle) { 
   
    		articlesPanier = panierBean.getPanier();
    		log.info("1 La taille de la liste d'articles avant suppression est : " + articlesPanier.size());
		
    		ArticlePanier art = new ArticlePanier();
    		for (Iterator it = articlesPanier.iterator (); it.hasNext (); ) {
			 art = (ArticlePanier)it.next ();
			 log.info("ezezezeze : " + art.getArticle().getArticleid());
			if (art.getArticle().getArticleid() == articleId){
				break;
			}
		  }
		
		panierBean.supprimerArticle(art);
			
		log.info("2 La taille de la liste d'articles après suppression est : " + articlesPanier.size());
		articlesPanier = panierBean.getPanier();
		log.info("3 La taille de la liste d'articles après getPanier est : " + articlesPanier.size());
        	
    	montantTotal = panierBean.getMontantTotal();	       	        	      
    	}    		
    	return "panier";
	 }
	 
	 // Methode non utilisée
	 public String recalculerMontant(){
	     log.info("Appel de la methode recalculerMontant()");	     
	     Integer total = 0;
	     String valeur;	     

	     return null;
	 }
	 
	 public String afficherCommandesPrecedentes(PhaseEvent event) {
		 log.info("Appel de la methode afficherCommandesPrecedentes()");
			
		FacesContext context = FacesContext.getCurrentInstance();
	    HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
	    LoginBean lb = null;
	    lb = (LoginBean) session.getAttribute("loginBean");
    	Client cl = (Client) lb.getClient();	    	
    	Utilisateur u = (Utilisateur)cl;	    	
    	
    	if (null != u){
    		log.info("Utilisateur non null");
    	} else {
    		log.info("Utilisateur null");
    	}		
    	commandes = panierBean.afficherCommandesPrecedentes(u);			
				
	   return "historiqueCommandes";
	 }	
	 
	 public void lala(PhaseEvent event) {
		 log.info("Appel de la methode lala()");
	 }
}
