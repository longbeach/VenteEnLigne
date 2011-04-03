package com.eni.dvtejb.clientStruts2.action;

import java.util.Map;

import javax.naming.InitialContext;

import org.apache.struts2.interceptor.SessionAware;
import org.jboss.logging.Logger;

import com.eni.dvtejb.metier.entities.Adresse;
import com.eni.dvtejb.metier.entities.Client;
import com.eni.dvtejb.metier.entities.Utilisateur;
import com.eni.dvtejb.metier.sessions.FacadeRemote;
import com.opensymphony.xwork2.ActionSupport;

public class AccueilAction extends ActionSupport implements SessionAware {
	
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(AccueilAction.class);
	private Map session ;

	// On expose un client pour pré-populer la JSP profil.jsp
	private Client client;
	private Adresse adresse;

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	
	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}
	
	//  Récupère les données du profil en base et les affiche sur la page d'edition de profil.
	public String editerProfil() throws Exception {
		
		log.info("Debut de la methode editerPorfil()");
	
		Client cl = (Client)session.get("leclient"); 
		InitialContext initialContext = new InitialContext(); 
    	FacadeRemote facade = (FacadeRemote) initialContext.lookup("VenteEnLigne/FacadeBean/remote");        
		Utilisateur utilisateur = facade.findUtilisateurById(cl.getUtilisateurid());
		//Seul un utilisateur de type Client peut changer son profil dans l'application (pas administrateur, pas gestionnaire)
		cl = (Client)utilisateur;
		
		Adresse ad = (Adresse)session.get("ladresse"); 
		ad =  facade.findAdresseById(ad.getAdresseid());				
		
		if (null != cl){
			log.info("Le client n'est pas nul");
			log.info(cl.getPrenom());
		} else {
			log.info("Le client est nul");
		}
		
		// Cette affection permet d'alimenter l'instance client, utilisée dans la JSP profil
		client = cl;
		log.info("client.getUtilisateurid() : " + client.getUtilisateurid());
    	
		adresse = ad;
		return SUCCESS;	
	}
	
	// Récupère les données du profil modifié sur la page d'edition de profil
	// et renvoie vers la page de confirmation de modification de profil.
	// Lors de l'edition d'un profil, un utilisateur peut tout modifier, y compris son login.
	// Seul l'ID utilisateur reste inchangé et non modifiable.
	public String sauverProfil() throws Exception {
		log.info("Entrée dans la méthode sauverProfil()");

		Client cl = (Client)session.get("leclient"); 
		Adresse ad = (Adresse)session.get("ladresse"); 
		if (null == cl) {
			log.info("cl est nul");
		}
		if (null == ad) {
			log.info("ad est nul");
		}
		
		InitialContext initialContext = new InitialContext(); 
    	FacadeRemote facade = (FacadeRemote) initialContext.lookup("VenteEnLigne/FacadeBean/remote"); 
       
    	Client client2 = getClient();
    	if (null == client2){
    		log.info("client2 est nul");
    	} else {	    	    		    	
	    	client.setUtilisateurid(cl.getUtilisateurid());
	    	adresse.setAdresseid(ad.getAdresseid());

	    	boolean test = facade.updateClient(client, adresse);
    	}
		return SUCCESS;	
	}
	
	public void setSession(Map session) {
		this.session = session;
	}

	public Map getSession() {
		return session;
	}

}
