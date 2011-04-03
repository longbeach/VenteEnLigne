package com.eni.dvtejb.clientJSF2.services;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;

import javax.ejb.EJB;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.eni.dvtejb.metier.entities.Article;
import com.eni.dvtejb.metier.services.ServiceLocator;
import com.eni.dvtejb.metier.sessions.PanierBeanRemote;

@ManagedBean(name="ServicesVenteEnLigne")
@ApplicationScoped
public class Services {
	
	private static final Logger log = Logger.getLogger(Services.class);

	@EJB(name="VenteEnLigne/PanierBean/remote")
	private  PanierBeanRemote panierBean;
	
	public Article rechercheDetails(String numeroId){
		log.info("Appel de la methode rechercheDetails()");
		
   	    long lID = Long.parseLong(numeroId);   		
		Article monArticle = panierBean.findById(lID);		
		return monArticle;
	}
}
