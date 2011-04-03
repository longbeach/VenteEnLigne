package com.eni.dvtejb.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;

import com.eni.dvtejb.metier.sessions.CompteurStatefulLocal;
import com.eni.dvtejb.metier.sessions.CompteurStatelessLocal;

public class ClientCompteurServlet extends HttpServlet  {
	
	private static final Logger log = Logger.getLogger(ClientCompteurServlet.class);
	
	@EJB(name="VenteEnLigne/CompteurStatefulBean/local")
	private CompteurStatefulLocal compteurStatefulBean;
	
	@EJB(name="VenteEnLigne/CompteurStatelessBean/local")
	private CompteurStatelessLocal compteurStatelessBean;
	
	 protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {  
		    		
		log.info(" ********** Debut de la méthode doGet ************ ");
		
		int compteurStateful = compteurStatefulBean.incrementer();
		int compteurStateless = compteurStatelessBean.incrementer();
		
		response.setContentType("text/html"); 
	   	PrintWriter output = response.getWriter(); 
	   	 
	   	output.println("<CENTER>");
	   	output.println("Méthode appelée : " + request.getMethod() + "<BR>");
	   	output.println("Servlet ClientCompteurServlet exécutée." + "<BR>");
	   	
	   	output.println("Le compteur Stateful vaut : " + compteurStateful + "<BR>");
		output.println("Le compteur Stateless vaut : " + compteurStateless + "<BR>");
		
	 
		
	 }
}
