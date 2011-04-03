package com.eni.dvtejb.clientJSF2.beans;

import java.math.BigDecimal;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.log4j.Logger;

import com.eni.dvtejb.metier.entities.Adresse;
import com.eni.dvtejb.metier.entities.Client;
import com.eni.dvtejb.metier.sessions.ClientDAO;

@ManagedBean(name="leClient")
@ViewScoped
public class ClientBean {
	
	private static final Logger log = Logger.getLogger(ClientBean.class);

	@Pattern(regexp=".+@.+\\.[a-z]+", message="Le format de l'adresse email est incorrect.")
	private String email;
	
	@Size(min=10, message="Le numéro de fax doit contenir 10 chiffres.")
	private String fax;
	
	@Size(min=5, message="Le login doit contenir au moins 5 caractères.")
	private String login;
	
	@Size(min=2, message="Le nom doit contenir au moins 2 caractères.")
	private String nom;
	
	// Expression régulière pour un password fort
	@Pattern(regexp="(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{8,10})$", 
			message="Le mot de passe doit être compris entre 8 et 10 caractères, contenir au moins un chiffre et au moins une lettre. " +
					"Il ne doit pas contenir de caractères spéciaux.")
	private String password;
	
	@Size(min=2, message="Le prénom doit contenir au moins 2 caractères.")
	private String prenom;
	
	@Size(min=10, message="Le numéro de telephone doit contenir 10 chiffres.")
	private String telephone;
	
	@Size(min=2, message="Le titre doit contenir au moins 2 caractères.")
	private String titre;
	
	@Size(min=4, max=5, message="Le code postal doit contenir entre 4 et 5 chiffres.")
	@Min(value=1000, message="Le code postal doit être un chiffre supérieur ou égal à 1000")
	private String codepostal;
	
	private String departement;
	private BigDecimal numero;
	
	@Size(min=3, message="Le pays doit contenir au moins 3 caractères")  
	private String pays;
	
	private String rue;
	private String ville;
	private String succes;
	private Client nouveauClient;
	private Adresse adresseClient;
	private boolean loginPwdOK = false;
	private boolean infosOK = false;
	private boolean adresseOK = false;
	
	public boolean isAdresseOK() {
		return adresseOK;
	}
	public void setAdresseOK(boolean adresseOK) {
		this.adresseOK = adresseOK;
	}

	@EJB(name="VenteEnLigne/ClientDAOImpl/remote")
	ClientDAO clientDAO;  

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	
	public String getSucces() {
		return succes;
	}
	public void setSucces(String succes) {
		this.succes = succes;
	}
	public String getCodepostal() {
		return codepostal;
	}
	public void setCodepostal(String codepostal) {
		this.codepostal = codepostal;
	}
	public String getDepartement() {
		return departement;
	}
	public void setDepartement(String departement) {
		this.departement = departement;
	}
	public BigDecimal getNumero() {
		return numero;
	}
	public void setNumero(BigDecimal numero) {
		this.numero = numero;
	}
	public String getPays() {
		return pays;
	}
	public void setPays(String pays) {
		this.pays = pays;
	}
	public String getRue() {
		return rue;
	}
	public void setRue(String rue) {
		this.rue = rue;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	
	public boolean isInfosOK() {
		return infosOK;
	}
	public void setInfosOK(boolean infosOK) {
		this.infosOK = infosOK;
	}
	public boolean isLoginPwdOK() {
		return loginPwdOK;
	}
	public void setLoginPwdOK(boolean loginPwdOK) {
		this.loginPwdOK = loginPwdOK;
	}
	
	public void validerLoginPwd(){
		log.info("Entree dans la methode validerLoginPwd()");
		log.info("login vaut : " + login);
		
		nouveauClient = new Client();
		if (null == nouveauClient) {
			log.info("nouveauClient est null");
		}
		
		nouveauClient.setLogin(login);
		nouveauClient.setPassword(password);
		loginPwdOK = true;
		return;		
	}
	
	public void validerInfos(){
		log.info("Entree dans la methode validerInfos()");
		
		if (null == nouveauClient) {
			log.info("validerInfos :: nouveauClient est null");
		}
		BigDecimal tel = new BigDecimal(telephone);
		BigDecimal fx = new BigDecimal(fax);
		nouveauClient.setNom(nom);
		nouveauClient.setPrenom(prenom);
		nouveauClient.setEmail(email);
		nouveauClient.setFax(fx);
		nouveauClient.setTelephone(tel);
		nouveauClient.setTitre(titre);		
		infosOK = true;
		return;		
	}
	
	public void validerAdresse(){
		log.info("Entree dans la methode validerAdresse()");
		
		adresseClient = new Adresse();
		BigDecimal cp = new BigDecimal (codepostal); 
		adresseClient.setCodepostal(cp);
		adresseClient.setDepartement(departement);
		adresseClient.setNumero(numero);
		adresseClient.setRue(rue);
		adresseClient.setVille(ville);
		adresseClient.setPays(pays);
		nouveauClient.setAdresseFk(adresseClient);
		adresseOK=true;
		return;		
	}
		
	public void validerInscription(){
		log.info("Entree dans la methode validerInscription()");
		
		try {
			clientDAO.save(nouveauClient);
			succes = "Inscription réussie !";
		} catch (Exception e ){
    	}   
		return;		
	}	
}
