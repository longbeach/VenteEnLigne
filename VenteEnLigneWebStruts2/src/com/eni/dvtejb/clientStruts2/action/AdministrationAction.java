package com.eni.dvtejb.clientStruts2.action;

import java.util.List;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.eni.dvtejb.metier.entities.Client;
import com.eni.dvtejb.metier.entities.Utilisateur;
import com.eni.dvtejb.metier.services.ServiceLocator;
import com.eni.dvtejb.metier.sessions.UtilisateurDAOBeanRemote;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class AdministrationAction implements  ServletRequestAware, Preparable, ModelDriven<Utilisateur> {
	
	private static final Logger log = Logger.getLogger(AdministrationAction.class);
	
	private UtilisateurDAOBeanRemote utilisateurDAO;
    private List<Utilisateur> utilisateurs;
    private Utilisateur utilisateur;
    private Integer utilisateurid;
    private HttpSession mSession;
	private HttpServletRequest mRequest;		
	   
    public void setServletRequest(HttpServletRequest request){
	    mRequest = request;
	    mSession = mRequest.getSession();
	}
    
    @Override
      public Utilisateur getModel() {    	
    	log.info("Recuperation du modele - utilisateur vaut : " + utilisateur);
        return utilisateur;
      }
    
    public AdministrationAction() {
    }
    
    public void initialisation()  {

		try {
			UtilisateurDAOBeanRemote utilisateurDAO = (UtilisateurDAOBeanRemote) ServiceLocator.getInstance().getService("VenteEnLigne/UtilisateurDAOBean/remote");	    	
			this.utilisateurDAO = utilisateurDAO;
		} catch (NamingException e) {
			e.printStackTrace();
		}     	    	
    }
 
	public String execute() {
		log.info("------------------------------ Debut methode execute()");
		this.utilisateurs = utilisateurDAO.rechercherTous();
		log.info("------------------------------ Avant Action.SUCCESS");		 
	    mRequest.setAttribute("utilisateur",utilisateur);		  
    	return Action.SUCCESS;
    }
	
	public String rechercheTous() {
		log.info("------------------------------ Debut methode list2()");
	    this.utilisateurs = utilisateurDAO.rechercherTous();        
		log.info("------------------------------ Fin methode list2()");
		return Action.SUCCESS;
    }
		
	public String save() {
		log.info("--------------Debut save())");
		if (null == utilisateur) {
			log.info("------------------- utilisateur est null ********)");
		}
		
		if (null != utilisateur) {
			log.info(" ===> utilisateur est non null"); 			
		}
        this.utilisateurDAO.sauver(utilisateur);
        log.info("--------------Fin save())");
        return execute();
    }

	public String remove() {
		log.info("------------------------------ Debut methode remove() -- id vaut : " + utilisateurid);
		if (null == utilisateurDAO) {
			log.info("-----------utilisateurDAO est nul");
		}
		if (null == utilisateur) {
			log.info("-----------utilisateur est nul");
		} else {
			log.info("-----------utilisateur.getUtilisateurid() : " + utilisateur.getUtilisateurid());
		}

		long l = Long.valueOf(utilisateur.getUtilisateurid()).longValue(); 
		log.info("------------------------------ l vaut : " + l);		
		utilisateurDAO.supprimer(l);
        return execute();
    }

	public List<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

	public Integer getUtilisateurid() {
        return utilisateurid;
    }

	public void setUtilisateurid(Integer utilisateurid) {
        this.utilisateurid = utilisateurid;
    }

	/*
	 * Cette méthode est appelée avant toute méthode par le framework
	 * @see com.opensymphony.xwork2.Preparable#prepare()
	 */
	public void prepare() throws Exception {
		log.info("------------------------------ Debut methode prepare()");
							
		initialisation() ;
		
        if (utilisateurid != null){
        	log.info("------------------------------ utilisateurid n'est pas null");
            utilisateur = utilisateurDAO.rechercher(utilisateurid);
            mSession.setAttribute("utilisateur", utilisateur);
        } else {
        	log.info("------------------------------ utilisateurid est null");
        	utilisateur = new Client();
        }        
    }

	public Utilisateur getUtilisateur() {
        return utilisateur;
    }

	public void setPerson(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}


