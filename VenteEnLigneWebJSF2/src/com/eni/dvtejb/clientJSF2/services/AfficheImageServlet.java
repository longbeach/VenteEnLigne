package com.eni.dvtejb.clientJSF2.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.jboss.logging.Logger;

import com.eni.dvtejb.metier.entities.Article;
import com.eni.dvtejb.metier.services.ServiceLocator;
import com.eni.dvtejb.metier.sessions.PanierBeanRemote;

public class AfficheImageServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(AfficheImageServlet.class);
	
	  protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {  
		    		
		log.info(" ********** Debut de la méthode doGet ************ ");

	      PanierBeanRemote panierBeanRemote = null;
		try {
			panierBeanRemote = (PanierBeanRemote) ServiceLocator.getInstance().getService("VenteEnLigne/PanierBean/remote");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	      String numeroId = (String)request.getParameter("numeroId");

	      log.info(" ********** numeroId vaut ************ " + numeroId);
	      // Conversion de String en long 
	   	  long l = Long.parseLong(numeroId);
	   	    
	      log.info(" ********** Debut de la recherche ************ ");
	      Article monArticle = panierBeanRemote.findById(l); 
	      log.info(" ********** Fin de la recherche ************ ");
	      response.setContentType("image/jpg");  
	      OutputStream out = response.getOutputStream();  
	  
	      // Un Blob est essentiellement un tableau de bytes
	      Blob image = null;		  
	      try {
			image = new SerialBlob(monArticle.getImage());
		} catch (SerialException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		  InputStream input = null;
		try {
			input = image.getBinaryStream();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  // Afficher l'image Blob sur la réponse HttpServletResponse
		  response.setContentType("image/jpg");  
	    
		  int taille = 0;
		  int bufferSize = 1024;
	      byte[] buffer = new byte[bufferSize];
	      
	      log.info(" ********** Avant ecriture ************ ");
	      while ((taille = input.read(buffer)) != -1) {
	          out.write(buffer, 0, taille);
	      }
	      
	      log.info(" ********** Fin de la méthode doGet ************ ");
	    }  

}
