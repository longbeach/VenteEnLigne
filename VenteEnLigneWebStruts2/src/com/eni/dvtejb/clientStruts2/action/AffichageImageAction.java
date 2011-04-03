package com.eni.dvtejb.clientStruts2.action;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;

import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;

import org.apache.struts2.ServletActionContext;
import org.jboss.logging.Logger;

import com.eni.dvtejb.metier.entities.Article;
import com.eni.dvtejb.metier.services.ServiceLocator;
import com.eni.dvtejb.metier.sessions.PanierBeanRemote;
import com.opensymphony.xwork2.ActionSupport;

public class AffichageImageAction extends ActionSupport { 

	private static final long serialVersionUID = 1L;	
	private static final Logger log = Logger.getLogger(AffichageImageAction.class);
	 	
	public String execute() throws Exception{  		
	  log.info(" ********** Debut de la méthode execute ************ ");
	 
	  PanierBeanRemote panierBeanRemote = (PanierBeanRemote) ServiceLocator.getInstance().getService("VenteEnLigne/PanierBean/remote");
      String numeroId = (String)ServletActionContext.getRequest().getParameter("numeroId");

      log.info(" ********** numeroId vaut ************ " + numeroId);
      // Conversion de String en long 
   	  long l = Long.parseLong(numeroId);
   	    
      log.info(" ********** Debut de la recherche ************ ");
      Article monArticle = panierBeanRemote.findById(l); 
      log.info(" ********** Fin de la recherche ************ ");
      HttpServletResponse response = ServletActionContext.getResponse();  
      OutputStream out = response.getOutputStream();  
  
      // Un Blob est essentiellement un tableau de bytes
      Blob image = null;		  
      image = new SerialBlob(monArticle.getImage());

	  InputStream input = image.getBinaryStream();
	  // Afficher l'image Blob sur la réponse HttpServletResponse
	  response.setContentType("image/jpg");  
    
	  int taille = 0;
	  int bufferSize = 1024;
      byte[] buffer = new byte[bufferSize];
      
      log.info(" ********** Avant ecriture ************ ");
      while ((taille = input.read(buffer)) != -1) {
          out.write(buffer, 0, taille);
      }
      
      log.info(" ********** Fin de la méthode execute ************ ");
  
      return null;  
    }  
}
