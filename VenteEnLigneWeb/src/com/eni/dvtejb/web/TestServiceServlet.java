package com.eni.dvtejb.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceRef;

import com.eni.dvtejb.metier.services.VerifierCarteCredit;

public class TestServiceServlet extends javax.servlet.http.HttpServlet {  

	private static final long serialVersionUID = 1L;
	
	@WebServiceRef(wsdlLocation="http://127.0.0.1:8085/VenteEnLigne-VenteEnLigneEJB/VerifierCarteCreditBean?wsdl")
     static   VerifierCarteCredit service;

	  protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {  
		  
	        boolean isValide = false ;
			 String numCC = "4716718496946025";
			 isValide = service.validerCarteCredit( "VISA", numCC);
			 
			 response.setContentType("text/html"); 
        	 PrintWriter output = response.getWriter(); 
        	 
        	 output.println("<CENTER>");
        	 output.println("Méthode appelée : " + request.getMethod() + "<BR>");
        	 output.println("Servlet TestServiceServlet exécutée." + "<BR>");
        	 
      
        	 if (isValide) {
        		 output.println("Le numero de carte de credit " + numCC + " est valide"); 			 
 			 } else {
 				 output.println("Le numero de carte de credit " + numCC + " est invalide");
 			 }  
        
        	 output.println("</CENTER>");
	    }  
}
