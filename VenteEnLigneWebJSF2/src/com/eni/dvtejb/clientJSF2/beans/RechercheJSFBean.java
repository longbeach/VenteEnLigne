package com.eni.dvtejb.clientJSF2.beans;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.PhaseEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.eni.dvtejb.metier.dtos.MagasinDTO;
import com.eni.dvtejb.metier.entities.Catalogue;
import com.eni.dvtejb.metier.entities.Produit;
import com.eni.dvtejb.metier.sessions.RechercheRemote;

@ManagedBean
@SessionScoped
public class RechercheJSFBean {
	
	private static final Logger log = Logger.getLogger(RechercheJSFBean.class);
	
	public RechercheJSFBean() {
	}

	private List<MagasinDTO> listeResultatArticles;
	private List<String> nomsProduitsListe;
	private List cataloguesListe;

	private String produitCritere;	
	private long catalogueCritere;
	
	public List<MagasinDTO> getListeResultatArticles() {
		return listeResultatArticles;
	}

	public void setListeResultatArticles(List<MagasinDTO> listeResultatArticles) {
		this.listeResultatArticles = listeResultatArticles;
	}
	
	public long getCatalogueCritere() {
		return catalogueCritere;
	}

	public void setCatalogueCritere(long catalogueCritere) {
		this.catalogueCritere = catalogueCritere;
	}
	
	public String getProduitCritere() {
		return produitCritere;
	}

	public void setProduitCritere(String produitCritere) {
		this.produitCritere = produitCritere;
	}

	public List<String> getNomsProduitsListe() {
		return nomsProduitsListe;
	}
	
	public List getCataloguesListe() {
		rechercheListeCatalogues();
		return cataloguesListe;
	}
	
	@EJB(name="VenteEnLigne/RechercheBean/remote")
	private  RechercheRemote rechercheBean;
	
	public void rechercheListeProduits(PhaseEvent event){
		log.info("Entrée dans la méthode rechercheListeProduits()");	
		List<Produit> listeProduits ;
		List<String> listeNomsProduits = new ArrayList<String>();
		listeProduits = rechercheBean.rechercheListeProduits();
		for (Produit produit : listeProduits){			  
		  listeNomsProduits.add(produit.getNom());
		}
		nomsProduitsListe = listeNomsProduits;		
	}
	
	public void rechercheListeCatalogues(){
		log.info("Entrée dans la méthode rechercheListeCatalogues()");	
		List<Catalogue> listeCatalogues ;
		List listeDesCatalogues = new LinkedList();
		listeCatalogues = rechercheBean.rechercheListeCatalogues();
	    for (Catalogue catalogue : listeCatalogues){			  
	    	listeDesCatalogues.add(new SelectItem(catalogue.getCatalogueid(), catalogue.getNom()));
	    }
	    cataloguesListe = listeDesCatalogues;		
	}
	
	 public String rechercheArticleParProduit(){
		log.info("Entrée dans la méthode rechercheArticleParProduit()");	
		List<MagasinDTO> listeArticles ;
		listeArticles = rechercheBean.rechercheArticleParProduit(produitCritere);		 
		listeResultatArticles = listeArticles;		  
		return "listeResultatRecherche";
	}
	 
	 public String rechercheArticleParCatalogue(){
		log.info("Entrée dans la méthode rechercheArticleParProduit()");	
		List<MagasinDTO> listeArticles ;
		listeArticles = rechercheBean.rechercheArticleParCatalogue(catalogueCritere);		 
		listeResultatArticles = listeArticles;		  
		return "listeResultatRecherche";
	}
	 
	 public String rechercheArtParCatalogue(long catalogueId){
		 log.info("Entrée dans la méthode rechercheArticleParProduit()");	
			List<MagasinDTO> listeArticles ;
			listeArticles = rechercheBean.rechercheArticleParCatalogue(catalogueId);		 
			listeResultatArticles = listeArticles;		  
			return "listeResultatRecherche";	
		}
}
