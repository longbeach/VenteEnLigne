package com.eni.dvtejb.metier.sessions;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.SecurityDomain;

import com.eni.dvtejb.metier.entities.Article;
import com.eni.dvtejb.metier.entities.Stock;


//venteEnLigne_domaine_DB : domaine de sécurité pour authentication /authentification via la base de données
//venteEnLigne_domaine : domaine de sécurité pour authentication /authentification via venteEnLigne-users.properties et venteEnLigne-roles.properties
//venteEnLigne_domaine_LDAP : domaine de sécurité pour authentication /authentification via LDAP
@SecurityDomain("venteEnLigne_domaine_DB") 
@RolesAllowed({"A", "G"})
@Stateful
public class ArticleDAOBean implements ArticleDAOBeanRemote {

	private static final Logger log = Logger.getLogger(ArticleDAOBean.class);
	
	@PersistenceContext
	private EntityManager em;
	
    /**
     * Default constructor. 
     */
    public ArticleDAOBean() {
        // TODO Auto-generated constructor stub
    }
    
    @RolesAllowed({"A", "G"}) // Administrateurs et gestionnaires sont habilités à lister les articles
	public List<Article> rechercherTous() {
		log.info("Debut methode rechercherTous()" );
		   
		Query query = em.createQuery("select a from Article a");
        return query.getResultList();
    }
		
	@RolesAllowed({"A"})  // Administrateurs et gestionnaires sont habilités à modifier des articles
	public void sauver(Article article) {
	   log.info("Debut methode sauver() ------------" );
		 
	   if (null == article) {
		   log.info("article est nul");
	   } else {
		   log.info("article n'est pas null ");
	   }
       Long articleId = article.getArticleid();
       log.info("articleId vaut : " + articleId);       
                   	
        if (null == articleId.toString() || 0 == articleId ) {
            // nouveau
            em.persist(article); // Persiste également le stock.
            
        } else {
            // modification 
        	Stock stock = article.getStockFK();
        	  log.info("Avant merge stock");
        	  if (null == stock) {
        		  log.info("stock est null");
        	  }
     
        	em.merge(stock);        	
            em.merge(article);
        }
    }

	@RolesAllowed({"A"}) // Administrateurs et gestionnaires sont habilités à supprimer des articles
	public void supprimer(long articleId) {
		 log.info("Debut methode supprimer()" );
	
		 log.info("articleId vaut : " + articleId );
		 Article article = rechercher(articleId);
        if (article != null) {
       	 log.info("article trouve !" );

            em.remove(article); 
        }
    }

	@RolesAllowed({"A", "G"}) // Administrateurs et gestionnaires sont habilités à lister les articles
	public Article rechercher(long articleId) {
		 log.info("Debut methode rechercher()" );
		 
		 if (null != em) {
			 log.info("em n'est pas nul" );
		 }
		 				 
		 Article art = null;
		 art = em.find(Article.class, articleId);
		 if (null != art) {
			 log.info("art n'est pas nul" );
		 }
        return art;
    }

}
