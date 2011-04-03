package com.eni.dvtejb.clientJSF2.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.jboss.web.tomcat.security.login.WebAuthentication;

import com.eni.dvtejb.metier.entities.Administrateur;
import com.eni.dvtejb.metier.entities.Adresse;
import com.eni.dvtejb.metier.entities.Client;
import com.eni.dvtejb.metier.entities.Gestionnaire;
import com.eni.dvtejb.metier.entities.Utilisateur;
import com.eni.dvtejb.metier.sessions.FacadeRemote;

@ManagedBean
@SessionScoped
public class LoginBean {
	
	private static final Logger log = Logger.getLogger(LoginBean.class);
	
	// constructeur publique sans argument
	public LoginBean() {	}

	private String login;
	private String password;
	private String email;
	private Client client;
	private Adresse adresse;
	private Administrateur admin;
	private Gestionnaire gest;
	private String succesModifProfil;
	
	public String getSuccesModifProfil() {
		return succesModifProfil;
	}

	public void setSuccesModifProfil(String succesModifProfil) {
		this.succesModifProfil = succesModifProfil;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}
	
	// Debut Pour la gestion de la langue à partir de la drop down box
	private List langues;
	private String langue ="FR";

	public String getLangue() {
		return langue;
	}

	public void setLangue(String langue) {
		this.langue = langue;
	}

	public List getLangues() {
		 if (langues == null) {
			 langues = new ArrayList<SelectItem>();
			 langues.add(new SelectItem("defaut", "---------"));
			 langues.add(new SelectItem("FR", "En français"));
			 langues.add(new SelectItem("UK", "In english"));
		 }
		return langues;
	}
	// Fin Pour la gestion de la langue à partir de la drop down box
	
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
		
  	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// Par défaut, la page login.xhtml sera en français
	private Locale locale = Locale.getDefault();
	
	public Locale getLocale() {
		log.info("Appel de la méthode getLocale()");	
		return locale;		
	} 	
	
	public void setLocale(Locale newValue) {locale = newValue;}  
	
	@EJB(name="VenteEnLigne/FacadeBean/remote")
	private  FacadeRemote facade; 
	
	public static void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, facesMsg);
    }

	public String verifierLogin() throws Exception {		
		log.info("Appel de la méthode verifierLogin()");
		
    	Utilisateur utilisateur = facade.findUtilisateurByLoginPwd(login, password);   	
    	
    	// Authentification via le container Web, pour la gestion des rôles
    	WebAuthentication pwl = new WebAuthentication();
    	pwl.login(login, password);
 
    	
    	if (null == utilisateur){
    		addErrorMessage("Mauvais login et/ou mauvais mot de passe, veuillez réessayer.");
    		 return "login";
	    } else {	    	
	    	if (utilisateur instanceof Client) {
	    		log.info("=========> L'utilisateur est un client ! ");
	    		client = (Client)utilisateur;
	    		adresse = client.getAdresseFk();
	    	} else if (utilisateur instanceof Administrateur) {
	    		log.info("=========> L'utilisateur est un Administrateur ! ");
	    		
	    		admin = (Administrateur)utilisateur;
	    		return "/administration/administration";
	    	} else if (utilisateur instanceof Gestionnaire) {
	    		log.info("=========> L'utilisateur est un Gestionnaire ! ");
	    		
	    		gest = (Gestionnaire)utilisateur;	    		
	    		return "/administration/administration";
	    	}
	    	return "/principal/accueil";
	    }
	}
	
	public String deconnexion() {
		log.info("Appel de la méthode deconnexion()");
		
	    FacesContext context = FacesContext.getCurrentInstance();
	    HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
	    
	    if (null == session) {
	    	log.info("Cas invalide");
	        return "invalide"; // Non traité
	    }  else {
	    	log.info("Cas valide");
	        session.invalidate();
	        login = null;
	        password = null;
	        return "/index";
	    }
	  }  
	
	public String changerLangue() {
		log.info("Appel de la méthode changerLangue()");
		
	    FacesContext context = FacesContext.getCurrentInstance();
		
		// Récupération du paramètre langue
		Map map = context.getExternalContext().getRequestParameterMap(); 
		String langue = (String) map.get("langue");
		
		if ("fr".equals(langue)){
			log.info("CAS FR");
		    context.getViewRoot().setLocale(Locale.FRENCH);
		    this.locale = Locale.FRENCH;	
		} else   {
			log.info("CAS EN");
			 context.getViewRoot().setLocale(Locale.UK);
			 this.locale = Locale.UK;			 	
		}

		log.info("La locale vaut: " + context.getViewRoot().getLocale());			

		context.getApplication().setDefaultLocale(context.getViewRoot().getLocale());

		return "index";
	  }  
	
	public void oubliMDP() {
		log.info("Appel de la méthode oubliMDP()");
		String mdp = facade.findMdpByLoginEmail(login, email);				
		password = "Votre mot de passe est " + mdp;
	}
	public void sauverProfil() {
		log.info("Appel de la méthode sauverProfil()");
		log.info("client.getNom vaut :" + client.getNom());
		log.info("adresse.getPays vaut :" + adresse.getPays());
		boolean profilMAJ = facade.updateClient(client, adresse);	
		if (profilMAJ){
			succesModifProfil="Modification réussie.";
		} else {
			succesModifProfil="La modification a échoué.";
		}
	}
	
	// Gestion de la langue à partir de la drop down box
	public void changerLangueComboBox(ValueChangeEvent event) {
		log.info("Appel de la méthode changerLangueComboBox()");		
		FacesContext context = FacesContext.getCurrentInstance();
		
		if ("en".equals((String) event.getNewValue())){
			log.info("Anglais --------------------");
			 context.getViewRoot().setLocale(Locale.UK);
			 this.locale = Locale.UK;	
		} else  if ("fr".equals((String) event.getNewValue())) {
			log.info("Français --------------------");
			 context.getViewRoot().setLocale(Locale.FRENCH);
			 this.locale = Locale.FRENCH;	
		} 
		// Appeler les phases UPDATE_MODEL_VALUES et INVOKE_APPLICATION
		log.info("context.getViewRoot().getLocale() vaut : " + context.getViewRoot().getLocale());
	}
}
