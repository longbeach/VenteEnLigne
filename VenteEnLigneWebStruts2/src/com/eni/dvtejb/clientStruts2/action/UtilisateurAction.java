package com.eni.dvtejb.clientStruts2.action;

import org.jboss.logging.Logger;

import com.eni.dvtejb.metier.entities.Adresse;
import com.eni.dvtejb.metier.entities.Client;
import com.eni.dvtejb.metier.services.ServiceLocator;
import com.eni.dvtejb.metier.sessions.ClientDAO;
import com.opensymphony.xwork2.ActionSupport;

public class UtilisateurAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(UtilisateurAction.class);
	
	private String email;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private Client client;
	private Adresse adresse;
	
	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public UtilisateurAction(){
	}
	
	public String execute() {
		log.info("entree dans la methode execute()");
        return SUCCESS;
    }

	public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
	
    public String validateLogin() throws Exception {
    	
    	boolean valide = true;
    	
    	if (client.getLogin().length()== 0) {
    	 addFieldError("client.login", "Login obligatoire");
    	 valide = false; 
    	}
    
    	if (client.getPassword().length()== 0) {
   		 addFieldError("client.password", "Password obligatoire");
   		 valide = false;    		 
    	}
    	
    	if (valide){
    		return SUCCESS;
    	}else {
    		return INPUT;	
    	}        	
    }
    
    public String validateUtilisateur() throws Exception {
    	
    	boolean valide = true;
    	
    	if (client.getLogin().length()== 0) {
    	 addFieldError("client.login", "Login obligatoire");
    	 valide = false; 
    	}
    
    	if (client.getPassword().length()== 0) {
   		 addFieldError("client.password", "Password obligatoire");
   		 valide = false;    		 
    	}
    	
    	if (client.getNom().length()== 0) {
    		 addFieldError("client.nom", "Nom obligatoire");
    		 valide = false;
    	}
    	
    	if (client.getPrenom().length()== 0) {
   		 addFieldError("client.prenom", "Prenom obligatoire");
   		valide = false;
    	}
    	
    	if (client.getTelephone() == null) {
      		 addFieldError("client.telephone", "Telephone obligatoire");
      		valide = false;
       	}
    	
    	if (client.getTitre().length()== 0) {
      		 addFieldError("client.titre", "Titre obligatoire");
      		valide = false;
       	}
    	
    	if (client.getFax() == null) {
      		 addFieldError("client.fax", "Fax obligatoire");
      		valide = false;
       	}
    	
    	if (client.getEmail().length()== 0) {
      		 addFieldError("client.email", "Email obligatoire");
      		valide = false;
       	}
    	
    	if (valide){
    		return SUCCESS;
    	}else {
    		return INPUT;	
    	}  
    }
    
	public String creerNouvelUtilisateur() throws Exception {
		
	    log.info("Création d'un nouvel utilisateur");
		boolean valide = true;
    	
    	if (client.getLogin().length()== 0) {
    	 addFieldError("client.login", "Login obligatoire");
    	 valide = false; 
    	}
    	if (client.getPassword().length()== 0) {
   		 addFieldError("client.password", "Password obligatoire");
   		 valide = false;    		 
    	}
    	
    	if (client.getNom().length()== 0) {
    		 addFieldError("client.nom", "Nom obligatoire");
    		 valide = false;
    	}
    	
    	if (client.getPrenom().length()== 0) {
   		 addFieldError("client.prenom", "Prenom obligatoire");
   		valide = false;
    	}
    	
    	if (client.getTelephone() == null) {
      		 addFieldError("client.telephone", "Telephone obligatoire");
      		valide = false;
       	}
    	
    	if (client.getTitre().length()== 0) {
      		 addFieldError("client.titre", "Titre obligatoire");
      		valide = false;
       	}
    	
    	if (client.getFax() == null) {
      		 addFieldError("client.fax", "Fax obligatoire");
      		valide = false;
       	}
    	
    	if (client.getEmail().length()== 0) {
      		 addFieldError("client.email", "Email obligatoire");
      		valide = false;
       	}
		
		if (adresse.getCodepostal() == null) {
   		 addFieldError("adresse.codepostal", "Code postal obligatoire");
   		valide = false;
		}
		
		if (adresse.getDepartement().length()== 0) {
	   		 addFieldError("adresse.departement", "Departement obligatoire");
	   		valide = false;
		}
			
		if (adresse.getNumero() == null) {
	   		 addFieldError("adresse.numero", "Numero obligatoire");
	   		valide = false;
		}
			
		if (adresse.getPays().length()== 0) {
	   		 addFieldError("adresse.pays", "Pays obligatoire");
	   		valide = false;
		}
		
		if (adresse.getRue().length()== 0) {
	   		 addFieldError("adresse.rue", "Rue obligatoire");
	   		valide = false;
		}
		
		if (adresse.getVille().length()== 0) {
	   		 addFieldError("adresse.ville", "Ville obligatoire");
	   		valide = false;
		}
		
		if (!valide){
    		return INPUT;	
    	}  
			      
    	ClientDAO clientDAO = (ClientDAO) ServiceLocator.getInstance().getService("VenteEnLigne/ClientDAOImpl/remote");	    	
		    	
    	client.setAdresseFk(adresse);
    	try {
    		clientDAO.save(client);
    	} catch (Exception e ){
    		addFieldError("client.email", "Cette adresse email est déjà utilisée. Veuillez rentrez une adresse email différente.");
    		return INPUT;	
    	}        
        return SUCCESS;
    }
 }