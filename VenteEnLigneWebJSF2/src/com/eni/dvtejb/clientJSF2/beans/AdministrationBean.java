package com.eni.dvtejb.clientJSF2.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.apache.log4j.Logger;

import com.eni.dvtejb.metier.entities.Article;
import com.eni.dvtejb.metier.entities.Client;
import com.eni.dvtejb.metier.entities.Produit;
import com.eni.dvtejb.metier.entities.Stock;
import com.eni.dvtejb.metier.entities.Utilisateur;
import com.eni.dvtejb.metier.sessions.ArticleDAOBeanRemote;
import com.eni.dvtejb.metier.sessions.UtilisateurDAOBeanRemote;

@ManagedBean
@SessionScoped
public class AdministrationBean {

	private static final Logger log = Logger.getLogger(AdministrationBean.class);
	
	public AdministrationBean() {
		log.info("CONSTRUCTEUR");
	} 
	
	private List listeClients;
	private List listeArticles;
	private Client clientCourant;
	private Article articleCourant;

	boolean desactive1; // bouton creation Client
	boolean desactive2; // bouton modification Client
	boolean render1; // Titre creation Client
	boolean render2; // Titre modification Client
	
	boolean desactive1Art; // bouton creation article
	boolean desactive2Art; // bouton modification article
	boolean render1Art; // Titre creation article
	boolean render2Art; // Titre modification article
	
	public boolean isDesactive1Art() {
		return desactive1Art;
	}

	public void setDesactive1Art(boolean desactive1Art) {
		this.desactive1Art = desactive1Art;
	}

	public boolean isDesactive2Art() {
		return desactive2Art;
	}

	public void setDesactive2Art(boolean desactive2Art) {
		this.desactive2Art = desactive2Art;
	}

	public boolean isRender1Art() {
		return render1Art;
	}

	public void setRender1Art(boolean render1Art) {
		this.render1Art = render1Art;
	}

	public boolean isRender2Art() {
		return render2Art;
	}

	public void setRender2Art(boolean render2Art) {
		this.render2Art = render2Art;
	}
	
	public Article getArticleCourant() {
		return articleCourant;
	}

	public void setArticleCourant(Article articleCourant) {
		this.articleCourant = articleCourant;
	}
	
	public boolean isDesactive1() {
		return desactive1;
	}

	public void setDesactive1(boolean desactive1) {
		this.desactive1 = desactive1;
	}

	public boolean isDesactive2() {
		return desactive2;
	}

	public void setDesactive2(boolean desactive2) {
		this.desactive2 = desactive2;
	}

	public boolean isRender1() {
		return render1;
	}

	public void setRender1(boolean render1) {
		this.render1 = render1;
	}

	public boolean isRender2() {
		return render2;
	}

	public void setRender2(boolean render2) {
		this.render2 = render2;
	}

	@EJB(name="VenteEnLigne/UtilisateurDAOBean/remote")
	private  UtilisateurDAOBeanRemote utilisateurDAO;
	
	@EJB(name="VenteEnLigne/ArticleDAOBeanRemote/remote")
	private ArticleDAOBeanRemote articleDAO;
	
	public Client getClientCourant() {
		return clientCourant;
	}

	public void setClientCourant(Client clientCourant) {
		this.clientCourant = clientCourant;
	}
	public List getListeClients() {
		rechercherClients();
		return listeClients;
	}
	
	public List getListeArticles() {
		rechercherArticles();
		return listeArticles;
	}

	//************* Debut DataModel pour liste des clients *****************
	DataModel<Client>  listeClientsModel  = new ListDataModel<Client>();
	
	public DataModel<Client> getListeClientsModel() {
		// Alimentation du DataModel à partir d'une liste
		listeClientsModel.setWrappedData(getListeClients());
		return listeClientsModel;
	}

	public void setListeClientsModel( DataModel<Client> listeClientsModel ) {
		this.listeClientsModel = listeClientsModel;
	}
	//*************Fin DataModel pour liste des clients *****************
	
	
	
	//************* Debut DataModel pour liste des articles *****************
	DataModel<Article>  listeArticlesModel  = new ListDataModel<Article>();
	
	public DataModel<Article> getListeArticlesModel() {
		// Alimentation du DataModel à partir d'une liste
		listeArticlesModel.setWrappedData(getListeArticles());
		return listeArticlesModel;
	}

	public void setListeArticlesModel( DataModel<Article> listeArticlesModel ) {
		this.listeArticlesModel = listeArticlesModel;
	}
	//*************Fin DataModel pour liste des articles *****************
	
	/*
	 * Retourne la liste des clients
	 */
	public void rechercherClients() {
		log.info("Entrée dans la méthode rechercherClients()");
		List<Utilisateur> listeUtilisateurs = new ArrayList<Utilisateur>();		
		listeUtilisateurs =  utilisateurDAO.rechercherTous();
		listeClients = listeUtilisateurs;	
	}
	
	/*
	 * Suppression d'un client
	 */
	public void supprimerClient() {
		log.info("Entrée dans la méthode supprimerClient()");
		
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();
		String numeroId = params.get("id");
		
		long l = Long.valueOf(numeroId); 
		log.info("------------------------------ l vaut : " + l);		
		utilisateurDAO.supprimer(l);
	}
	
	/*
	 * Modification d'un client
	 */
	public String  modifier() {
		log.info("-----------------------------------------------------------");
		log.info("Entrée dans la méthode modifier()");
		log.info("-----------------------------------------------------------");		
		clientCourant = (Client)listeClientsModel.getRowData();		
		log.info("clientCourant.getNom() vaut : " + clientCourant.getNom());
		
		desactive1=true;
		desactive2=false;
	    render1=false;
	    render2=true;
	    
		return "sauverClient?faces-redirect=true";
	}
	
	/*
	 * Ajout d'un client
	 */
	public String  ajouter() {
		log.info("-----------------------------------------------------------");
		log.info("Entrée dans la méthode ajouter()");
		log.info("-----------------------------------------------------------");		
		clientCourant = new Client();
		
		desactive1=false;
		desactive2=true;
	    render1=true;
	    render2=false;
	    
		return "sauverClient?faces-redirect=true";
	}
	
	/*
	 * Ajout / Modification d'un client
	 */
	public void  sauverClient() {
		log.info("-----------------------------------------------------------");
		log.info("Entrée dans la méthode sauverClient()");
		log.info("-----------------------------------------------------------");
		log.info("clientCourant.getUtilisateurid() vaut : " + clientCourant.getUtilisateurid());
		 utilisateurDAO.sauver(clientCourant);
		 FacesContext context = FacesContext.getCurrentInstance();
		 context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès -", "Opération effectuée avec succès !"));
	}
	
	/*
	 * Retourne la liste des articles
	 */
	public void rechercherArticles() {
		log.info("Entrée dans la méthode rechercherArticles()");
		List<Article> listeDesArticles = new ArrayList<Article>();		
		listeDesArticles =  articleDAO.rechercherTous();
		listeArticles = listeDesArticles;	
	}
	
	/*
	 * Suppression d'un article
	 */
	public void supprimerArticle() {
		log.info("Entrée dans la méthode supprimerArticle()");
		
		// TODO : recuperer le client avec getRowData() au lieu d'utiliser un param id
		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();
		String numeroId = params.get("id");
		
		long l = Long.valueOf(numeroId); 
		log.info("------------------------------ l vaut : " + l);		
		articleDAO.supprimer(l);
	}
	
	/*
	 * Modification d'un article
	 */
	public String  modifierArticle() {
		log.info("-----------------------------------------------------------");
		log.info("Entrée dans la méthode modifierArticle()");
		log.info("-----------------------------------------------------------");		
		articleCourant = (Article)listeArticlesModel.getRowData();		
		log.info("articleCourant.getNom() vaut : " + articleCourant.getNom());
		
		desactive1Art=true;
		desactive2Art=false;
	    render1Art=false;
	    render2Art=true;
	    
		return "sauverArticle?faces-redirect=true";
	}
	
	/*
	 * Ajout d'un article
	 */
	public String  ajouterArticle() {
		log.info("-----------------------------------------------------------");
		log.info("Entrée dans la méthode ajouterArticle()");
		log.info("-----------------------------------------------------------");		
		articleCourant = new Article();
		
		// Pas nécessaire avec Struts 2 !! (check)
		Produit produitFk = new Produit();
		Stock stockFK = new Stock();
		articleCourant.setProduitFk(produitFk);
		articleCourant.setStockFK(stockFK);
		
		desactive1Art=false;
		desactive2Art=true;
	    render1Art=true;
	    render2Art=false;
	    
		return "sauverArticle?faces-redirect=true";
	}
	
	/*
	 * Ajout / Modification d'un article
	 */
	public void  sauverArticle() {
		log.info("-----------------------------------------------------------");
		log.info("Entrée dans la méthode sauverArticle()");
		log.info("-----------------------------------------------------------");
		log.info("getArticleid() vaut : " + articleCourant.getArticleid());
		 articleDAO.sauver(articleCourant);
		 FacesContext context = FacesContext.getCurrentInstance();
		 context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Succès -", "Opération effectuée avec succès !"));
	}
	
}
