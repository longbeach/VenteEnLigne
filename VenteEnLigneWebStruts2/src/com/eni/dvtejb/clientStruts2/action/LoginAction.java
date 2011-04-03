package com.eni.dvtejb.clientStruts2.action;

import java.util.Map;

import javax.naming.InitialContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.jboss.logging.Logger;
import org.jboss.web.tomcat.security.login.WebAuthentication;

import com.eni.dvtejb.metier.entities.Administrateur;
import com.eni.dvtejb.metier.entities.Adresse;
import com.eni.dvtejb.metier.entities.Client;
import com.eni.dvtejb.metier.entities.Gestionnaire;
import com.eni.dvtejb.metier.entities.Utilisateur;
import com.eni.dvtejb.metier.sessions.FacadeRemote;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

public class LoginAction extends ActionSupport implements SessionAware {
	
	private static final Logger log = Logger.getLogger(LoginAction.class);
	
	private Map session;
	private Client client;
	private Administrateur admin;
	private Gestionnaire gest;
	
	public Administrateur getAdmin() {
		return admin;
	}

	public void setAdmin(Administrateur admin) {
		this.admin = admin;
	}

	public Gestionnaire getGest() {
		return gest;
	}

	public void setGest(Gestionnaire gest) {
		this.gest = gest;
	}

	public Client getClient() {
		return client;
	}
	
	public void setClient(Client client) {
		this.client = client;
	}

	private static final long serialVersionUID = 1L;
	
	public String changerLangue()throws Exception {
		log.info("Entrée dans la méthode changerLangue()");	
		return INPUT;
	}
		
	public String execute() throws Exception {	
		
		log.info("Entrée dans la méthode execute() ");
        
    	InitialContext initialContext = new InitialContext(); 
    	FacadeRemote facade = (FacadeRemote) initialContext.lookup("VenteEnLigne/FacadeBean/remote");            	
		if (null == facade) {
			log.info("facade est null ");			
		}
	
    	Utilisateur utilisateur = facade.findUtilisateurByLoginPwd(getLogin(), getPassword());
    	
    	// Authentification via le container Web, pour la gestion des rôles
    	WebAuthentication pwl = new WebAuthentication();
    	pwl.login(getLogin(), getPassword());
        	
    	log.info("User Principal="+ServletActionContext.getRequest().getUserPrincipal());  	
    	
    	if (utilisateur instanceof Client) {
    		log.info("=========> L'utilisateur est un client ! ");
    		client = (Client)utilisateur;
    	} else if (utilisateur instanceof Administrateur) {
    		log.info("=========> L'utilisateur est un Administrateur ! ");
    		
    		 admin = (Administrateur)utilisateur;
    		Adresse adresse = admin.getAdresseFk();
    		session.put("lutilisateur", admin); 
    		session.put("ladresseUtilisateur", adresse);
    		
    		return "administration";
    	} else if (utilisateur instanceof Gestionnaire) {
    		log.info("=========> L'utilisateur est un Gestionnaire ! ");
    		
    		gest = (Gestionnaire)utilisateur;
    		Adresse adresse = gest.getAdresseFk();
    		session.put("lutilisateur", gest); 
    		session.put("ladresseUtilisateur", adresse);
    		
    		return "administration";
    	}
    	    	
    if (null == client) {
            addActionError("Mauvais login et/ou mauvais mot de passe, veuillez réessayer.");
            return ERROR;
    } else {    
    	
    	Adresse adresse = client.getAdresseFk();
    	log.info("code postal du client : " + adresse.getCodepostal());    	
    	log.info("LoginAction - client.getUtilisateurid() : " + client.getUtilisateurid());
    	
		session.put("leclient", client); 
		session.put("ladresse", adresse);
		
      return SUCCESS;
    }
  }		
	
	public void setSession(Map session) {
		this.session = session;
	}

	public Map getSession() {
		return session;
	}
	
    private String login = null;
   
    @RequiredStringValidator(message="Le login est obligatoire !!", trim=true)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
    	this.login = login;
    }

    private String password = null;

    @RequiredStringValidator(message="Le password est obligatoire !", trim=true)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
