package com.eni.dvtejb.clientStruts2.action;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceFactory;

import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.SessionAware;
import org.jboss.logging.Logger;

import com.eni.dvtejb.metier.entities.Client;
import com.eni.dvtejb.metier.entities.Commande;
import com.eni.dvtejb.metier.services.ArticlePanier;
import com.eni.dvtejb.metier.services.ServiceLocator;
import com.eni.dvtejb.metier.services.VerifierCarteCredit;
import com.eni.dvtejb.metier.sessions.PanierBeanRemote;
import com.opensymphony.xwork2.ActionSupport;

public class PaiementAction extends ActionSupport implements SessionAware, ParameterAware  {
	
	private static final Logger log = Logger.getLogger(PaiementAction.class);
	
	private Map session ;
	private Map parameters;

	public Map getParameters() {
		return parameters;
	}

	public void setParameters(Map parameters) {
		this.parameters = parameters;
	}

	//@Override
	public void setSession(Map map) {
		this.session = map;	
	}
		
	String typeCC;
	String numCC;
	String nomTitulaire;
	Integer codeSecurite;	
	Integer moisExpiration;
	Integer anneeExpiration;
	
	String expirationDate;
	
	public String getExpirationDate() {
		return expirationDate;
	}
	
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getTypeCC() {
		return typeCC;
	}
	public void setTypeCC(String typeCC) {
		this.typeCC = typeCC;
	}
	public String getNumCC() {
		return numCC;
	}
	
	public void setNumCC(String numCC) {
		this.numCC = numCC;
	}
	public String getNomTitulaire() {
		return nomTitulaire;
	}
	public void setNomTitulaire(String nomTitulaire) {
		this.nomTitulaire = nomTitulaire;
	}
	public Integer getCodeSecurite() {
		return codeSecurite;
	}
	public void setCodeSecurite(Integer codeSecurite) {
		this.codeSecurite = codeSecurite;
	}
	public Integer getMoisExpiration() {
		return moisExpiration;
	}
	public void setMoisExpiration(Integer moisExpiration) {
		this.moisExpiration = moisExpiration;
	}
	public Integer getAnneeExpiration() {
		return anneeExpiration;
	}
	public void setAnneeExpiration(Integer anneeExpiration) {
		this.anneeExpiration = anneeExpiration;
	}
		
	// Paiement de la commande
	public String payer() throws Exception {
	    log.info("Entrée dans PaiementAction.payer() - ServiceFactory");
	    log.info("  ------------  getTypeCC vaut : " + getTypeCC());
	    log.info("  ------------ getNumCC vaut : " + getNumCC());	    
	    log.info("getExpirationDate vaut : " + getExpirationDate());
	    
		 boolean isValide = false ;
		
			    URL url = new URL("http://127.0.0.1:8085/VenteEnLigne-VenteEnLigneEJB/VerifierCarteCreditBean?wsdl");
		        QName qname = new QName("http://services.metier.dvtejb.eni.com/", "VerifierCarteCreditBeanService");
		
		        ServiceFactory factory = ServiceFactory.newInstance();
		        Service remote = factory.createService(url, qname);
		
		        VerifierCarteCredit verifiercc = (VerifierCarteCredit) remote.getPort(VerifierCarteCredit.class);

		        if (null == verifiercc){
		        	log.info("verifiercc est null");
		        } else   {
		        	log.info("verifiercc n'est pas null");
		        }
		        	
		 // Appel du Web Service de validation de numéro de carte de crédit
	     isValide = verifiercc.validerCarteCredit(getTypeCC(), getNumCC());
	        
	     if (isValide) {
	    	 log.info("Le numero de carte de credit " + numCC + " est valide");
	        		        	
	        	//******************************** INSERTION EN BASE DEBUT******************************
	        	
	        	Client cl = (Client)session.get("leclient");	    		
	    	    PanierBeanRemote panierBeanRemote = (PanierBeanRemote) ServiceLocator.getInstance().getService("VenteEnLigne/PanierBean/remote");
	    	    
	    	    ArrayList<ArticlePanier> articlesPanier;
	    		articlesPanier = panierBeanRemote.getPanier();
	    	
	    		// Conversion java.util.Date vers java.sql.Date
	    		
	    		java.util.Date utilDate = stringToDate(getExpirationDate());
	    		log.info(" ------------ utilDate vaut : " + utilDate);	    		
	    		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	    		log.info("  ------------ sqlDate vaut : " + sqlDate);
	    		
	    		panierBeanRemote.commander(cl, articlesPanier, numCC, typeCC, sqlDate);
	        	
	        	//******************************** INSERTION EN BASE  FIN******************************
	        	
	        	return SUCCESS;
	        } else {
	        	addActionError("Le numero de carte de credit " + numCC + " est invalide");
	        	log.info("Le numero de carte de credit " + numCC + " est invalide");
	        	return ERROR;
	        }			
	}
	
	 // datetimepicker renvoie une date de type String et au format 2009-11-24T00:00:00+01:00
	 public static Date stringToDate(String sDate) throws ParseException {
	  
		  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");	
	      return formatter.parse(sDate);	 
	 }

}
