package com.eni.dvtejb.clientStruts2.action;

import java.util.List;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.eni.dvtejb.metier.entities.Article;
import com.eni.dvtejb.metier.services.ServiceLocator;
import com.eni.dvtejb.metier.sessions.ArticleDAOBeanRemote;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class AdministrationArticlesAction implements  ServletRequestAware, Preparable, ModelDriven<Article> {

private static final Logger log = Logger.getLogger(AdministrationAction.class);
	
	private ArticleDAOBeanRemote articleDAO;
    private List<Article> articles;
    private Article article;
    private Integer articleid;
    private HttpSession mSession;
	private HttpServletRequest mRequest;		
	   
    public void setServletRequest(HttpServletRequest request){
	    mRequest = request;
	    mSession = mRequest.getSession();
	}
    
    @Override
      public Article getModel() {    	
    	log.info("Recuperation du modele - article vaut : " + article);
        return article;
      }
    
    public AdministrationArticlesAction() {
    }
    
    public void initialisation()  {
		try {
			ArticleDAOBeanRemote articleDAO = (ArticleDAOBeanRemote) ServiceLocator.getInstance().getService("VenteEnLigne/ArticleDAOBean/remote");	    	
			this.articleDAO = articleDAO;
		} catch (NamingException e) {
			e.printStackTrace();
		}     	    	
    }
 
	public String execute() {
		log.info("------------------------------ Debut methode execute()");
		this.articles = articleDAO.rechercherTous();
		log.info("------------------------------ Avant Action.SUCCESS");		 
	    mRequest.setAttribute("article", article);		  
    	return Action.SUCCESS;
    }
	
	public String rechercheTous() {
		log.info("------------------------------ Debut methode list2()");
	    this.articles = articleDAO.rechercherTous();        
		log.info("------------------------------ Fin methode list2()");
		return Action.SUCCESS;
    }
		
	public String save() {
		log.info("--------------Debut save())");
		if (null == article) {
			log.info("------------------- article est null ********)");
		}
		if (null != article) {
			log.info(" ===> article est non null"); 			
		}
        this.articleDAO.sauver(article);
        log.info("--------------Fin save())");
        return execute();
    }

	public String remove() {
		log.info("------------------------------ Debut methode remove() -- id vaut : " + articleid);
		if (null == articleDAO) {
			log.info("-----------articleDAO est nul");
		}
		if (null == article) {
			log.info("-----------article est nul");
		} else {
			log.info("-----------article() : " + article.getArticleid());
		}

		long l = Long.valueOf(article.getArticleid()).longValue(); 
		log.info("------------------------------ l vaut : " + l);		
		articleDAO.supprimer(l);
        return execute();
    }

	public List<Article> getArticles() {
        return articles;
    }

	public Integer getArticleid() {
        return articleid;
    }

	public void setArticleid(Integer articleid) {
        this.articleid = articleid;
    }

	/*
	 * Cette méthode est appelée avant toute méthode par le framework
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {
		log.info("------------------------------ Debut methode prepare()");
							
		initialisation() ;
		
        if (articleid != null){
        	log.info("------------------------------ utilisateurid n'est pas null");
        	article = articleDAO.rechercher(articleid);
            mSession.setAttribute("utilisateur", article);
        } else {
        	log.info("------------------------------ utilisateurid est null");
        	article = new Article();
        }        
    }

	public Article getArticle() {
        return article;
    }

	// A VIRER ???
	public void setPerson(Article article) {
        this.article = article;
    }
}
