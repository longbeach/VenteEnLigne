package com.eni.dvtejb.clientJSF2.beans;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.ServiceFactory;

import org.apache.log4j.Logger;

import com.eni.dvtejb.metier.entities.Client;
import com.eni.dvtejb.metier.services.ArticlePanier;
import com.eni.dvtejb.metier.services.VerifierCarteCredit;
import com.eni.dvtejb.metier.sessions.PanierBeanRemote;

@ManagedBean
@SessionScoped
public class PaiementBean {
	
	private static final Logger log = Logger.getLogger(PaiementBean.class);

	private String typeCC;
	private String numCC;	
	private String nomcc;
	private Integer codesecu;
	private Date expirationDate;
	
	public void setTypeCC(String typeCC) {
		this.typeCC = typeCC;
	}

	public void setNumCC(String numCC) {
		this.numCC = numCC;
	}

	public void setNomcc(String nomcc) {
		this.nomcc = nomcc;
	}

	public void setCodesecu(Integer codesecu) {
		this.codesecu = codesecu;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	
	@EJB(name="VenteEnLigne/PanierBean/remote")
	private  PanierBeanRemote panierBean;

	public String getTypeCC() {
		return typeCC;
	}
	
	public String getNumCC() {
		return numCC;
	}

	public String getNomcc() {
		return nomcc;
	}

	public Integer getCodesecu() {
		return codesecu;
	}

	public String payer()  {
		log.info("Debut de la methode payer()");
		
		 boolean isValide = false ;			
	    //********* appel du Web service : option A ******
	    // VerifierCarteCredit verifiercc = (VerifierCarteCredit) ServiceLocator.getInstance().getService("VenteEnLigne/VerifierCarteCreditBean/remote");
	    // 	     isValide = verifiercc.validerCarteCredit(getTypeCC(), getNumCC());
		  
	    // ********** appel du Web service : option B ************
	    // JAX-RPC
	    URL url = null;
		try {
			url = new URL("http://127.0.0.1:8085/VenteEnLigne-VenteEnLigneEJB/VerifierCarteCreditBean?wsdl");
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
        QName qname = new QName("http://services.metier.dvtejb.eni.com/", "VerifierCarteCreditBeanService");

        ServiceFactory factory;
        Service remote;
        VerifierCarteCredit verifiercc = null;
		try {
			factory = ServiceFactory.newInstance();
		     remote = factory.createService(url, qname);				
	         verifiercc = (VerifierCarteCredit) remote.getPort(VerifierCarteCredit.class);
		} catch (ServiceException e1) {
			e1.printStackTrace();
		}

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
          FacesContext context = FacesContext.getCurrentInstance();
    	    HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
    	    LoginBean lb = null;
    	    lb = (LoginBean) session.getAttribute("loginBean");

	    	Client cl = (Client) lb.getClient();
    	    ArrayList<ArticlePanier> articlesPanier;
    		articlesPanier = panierBean.getPanier();
	    	   		    		
    		java.sql.Date sqlDate = new java.sql.Date(getExpirationDate().getTime());
    		panierBean.commander(cl, articlesPanier, numCC, typeCC, sqlDate);        	
        	//******************************** INSERTION EN BASE  FIN******************************
    		log.info("FIN INSERTION");
    		return "paiementReussi";
	        } else {		        	
	        	addErrorMessage("Le numero de carte de credit " + numCC + " est invalide");
	        	log.info("Le numero de carte de credit " + numCC + " est invalide");
	        	return "paiement";
	        }	
	}
	
	 public static Date stringToDate(String sDate) throws ParseException {		 
		  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");	
	      return formatter.parse(sDate);	 
	 }
	
	 public static void addErrorMessage(String msg) {
	        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
	        FacesContext fc = FacesContext.getCurrentInstance();
	        fc.addMessage(null, facesMsg);
	 }
}
