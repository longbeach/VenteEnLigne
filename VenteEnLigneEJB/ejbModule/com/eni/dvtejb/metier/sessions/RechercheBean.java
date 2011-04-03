package com.eni.dvtejb.metier.sessions;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.eni.dvtejb.metier.dtos.MagasinDTO;
import com.eni.dvtejb.metier.entities.Article;
import com.eni.dvtejb.metier.entities.Catalogue;
import com.eni.dvtejb.metier.entities.Produit;

@Stateless
@Remote (RechercheRemote.class)
public class RechercheBean implements RechercheRemote, Serializable {

	private static final long serialVersionUID = 1L;	
	private static final Logger log = Logger.getLogger(RechercheBean.class);
		
	@PersistenceContext(unitName = "VenteEnLigneModuleEJB", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

	public List<Article> rechercheArticles(String nomArticle, BigDecimal prixMinimum, BigDecimal prixMaximum){
				 
		  log.info("Entrée dans la classe : " + getClass().getName());
		  log.info("Entrée dans la méthode : rechercheArticles()");		  
		  Query query = null;
		
		  if (nomArticle.equals("Tous")){			  
			   query = entityManager.createNativeQuery("SELECT a.articleid, a.nom, a.prix FROM article a WHERE " +
				" a.prix between ?1 and ?2");
				query.setParameter(1, prixMinimum);
				query.setParameter(2, prixMaximum);
		  } else {			  
			  query = entityManager.createNativeQuery("SELECT a.articleid, a.nom, a.prix FROM article a WHERE " +
				" a.nom = ?1 AND a.prix between ?2 and ?3");
				query.setParameter(1, nomArticle);
				query.setParameter(2, prixMinimum);
				query.setParameter(3, prixMaximum);
		  }
		
		List articles = query.getResultList();		
		return articles;
	}
	
	// Méthode pour autocomplete AJAX Dojo
    public List rechercheNomArticles(){		
    	log.info("Entrée dans la classe : " + getClass().getName());
    	log.info("Entrée dans la méthode : rechercheNomArticles()");		  
		Query query = entityManager.createNativeQuery("SELECT a.nom FROM article a");		
		List nomArticles = query.getResultList();		
		return nomArticles;
	}
    
	@SuppressWarnings("unchecked")
	public List<Produit> rechercheListeProduits(){
		log.info("Entrée dans la méthode rechercheListeProduits() " );
		Query q = entityManager.createNamedQuery("Produit.findByProduit"); 
		List<Produit> listeProduits = q.getResultList();	
		return listeProduits;		
	}
	
	@SuppressWarnings("unchecked")
	public List<Catalogue> rechercheListeCatalogues(){
		Query q = entityManager.createNamedQuery("Catalogue.findByCatalogue"); 		
		List<Catalogue> listeCatalogues = q.getResultList();		
		return listeCatalogues;		
	}
	
	@SuppressWarnings("unchecked") 
	public List<MagasinDTO> rechercheArticleParProduit (String nomProduit){
		log.info("Lancement de la methode  rechercheArticleParProduit");
		String requete = "SELECT NEW com.eni.dvtejb.metier.dtos.MagasinDTO( c.nom, p.nom, a.nom, a.articleid, a.prix, s.quantite) " +
		              " FROM Catalogue c, Produit p, Article a, Stock s" + 
		       		  " WHERE c.catalogueid = p.catalogueFk" +
					  " AND p.produitid = a.produitFk" +
					  " AND a.stockFK = s.stockid " +
				      " AND p.nom = :nomArt" ; 
		Query req= entityManager.createQuery(requete);
		req.setParameter("nomArt", nomProduit);
		return req.getResultList();		
	} 
	
	public List<MagasinDTO> rechercheArticleParCatalogue(long idCatalogue){
		log.info("Lancement de la methode rechercheArticleParCatalogue()");
		String requete = "SELECT NEW com.eni.dvtejb.metier.dtos.MagasinDTO( c.nom, p.nom, a.nom, a.articleid, a.prix, s.quantite) " +
		              " FROM Catalogue c, Produit p, Article a, Stock s" + 
		       		  " WHERE c.catalogueid = p.catalogueFk" +
					  " AND p.produitid = a.produitFk" +
					  " AND a.stockFK = s.stockid " +
				      " AND c.catalogueid = :idCat" ; 
		Query req= entityManager.createQuery(requete);
		req.setParameter("idCat", idCatalogue);
		return req.getResultList();	
	}
	
	// Utilisé par le projet Flex. Recherche d'articles par nom.
	// Exemple : P ==> renvoie tous les articles commençant par P
	public List<Article> rechercheArticleParNom(String nomArticle){
		
		  log.info("Entrée dans la classe : " + getClass().getName());
		  log.info("Entrée dans la méthode : rechercheArticleParNom()");		  
		  Query query = null;
					  
		  query = entityManager.createNativeQuery("SELECT a.articleid, a.nom, a.prix FROM article a WHERE " +
			" a.nom like ?1");
			query.setParameter(1, nomArticle + "%");
			
		List articles = query.getResultList();		
		return articles;
	}

}
