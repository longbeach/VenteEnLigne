package com.eni.dvtejb.web;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

// Le package org.hibernate.lob.SerializableBlob n'est pas présent dans JBoss 6.0 M1 !!
//import org.hibernate.lob.SerializableBlob;  
/**
 * Servlet implementation class DisplayBlobExample
 */
public class DisplayBlobExample extends HttpServlet {
	    
	  private EntityManagerFactory emFactory;
	  private EntityManager em;
	
	  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException {
		
		  emFactory = Persistence.createEntityManagerFactory("VenteEnLigneClientJavaEE");
		  em = emFactory.createEntityManager();	
						 
		 Query req = em.createNativeQuery("select a.image from Article a where articleid='13'");
		  
		  Blob image = null;		  
		 // List< SerializableBlob > resultats = req.getResultList();
		  
		 // for ( SerializableBlob  images : resultats) {
		 // image = images;
	  	 //	}	
		  List< Blob > resultats = req.getResultList();
		  
	      for ( Blob  images : resultats) {
		      try {
				image = new SerialBlob(images);
			} catch (SerialException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		  }	
		   
		  ServletOutputStream out = response.getOutputStream();  
		  
		  try
		  {
			  InputStream input = image.getBinaryStream();
			  response.setContentType("image/jpg");  
		    
			  int taille = 0;
			  int bufferSize = 1024;
		      byte[] buffer = new byte[bufferSize];
		      
		      while ((taille = input.read(buffer)) != -1) {
		          out.write(buffer, 0, taille);
		      }
		      
		      input.close();
		      out.flush();
		      out.close();
		  } catch(Exception e)
		  {
			 response.setContentType("text/html");
		     out.println("<html><head><title>Impossible d'afficher l'image.</title></head>");
		     out.println("<body><h4><font color='red'>Erreur = " + e.getMessage() + "</font></h4></body></html>");
		     
		     return;
 	      }
 	      	     
		  /*
		  Query req = em.createNativeQuery("select a.nom from Article a where articleid='14'");
		  List< String >  resultat = req.getResultList();
		  String nom = "titi";
		  for ( String  noms : resultat) {
			  nom =  	noms;
		    }
			out.println(nom);
		  */
  }
}
