package com.eni.dvtejb.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.logging.Logger;

import com.eni.dvtejb.metier.sessions.StockTimerServiceRemote;

public class TestTimer extends javax.servlet.http.HttpServlet {

	private static final long serialVersionUID = 1L; 
	
	private static final Logger log = Logger.getLogger(TestTimer.class);
	
	private static String DEMARRER = "demarrer";
	private static String STOPPER = "stopper";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {  
        try { 
        	
        	log.info(" ********** Debut TestTimer.doGet ************ ");
     
        	InitialContext initialContext = new InitialContext(); 
        	StockTimerServiceRemote stockTimerServiceRemote = (StockTimerServiceRemote) initialContext.lookup("VenteEnLigne/StockTimerService/remote"); 
           	
        	  String appel = request.getParameter("appel");
        	  
        	  if (DEMARRER.equals(appel)) {
              	stockTimerServiceRemote.verifierStocks();
        		  
        	  } else if (STOPPER.equals(appel)) {
        			stockTimerServiceRemote.arreterTimers();
        	  }
        	        	        	 
        	 response.setContentType("text/html"); 
        	 PrintWriter output = response.getWriter(); 
        	 
        	 output.println("<CENTER>");
        	 output.println("Méthode appelée : " + request.getMethod() + "<BR>");
        	 output.println("Servlet TestTimer exécutée." + "<BR>");
        	 output.println("Valeur du paramètre d'appel : " + request.getParameter("appel"));
        	 output.println("</CENTER>");

        	 log.info(" ********** Fin TestTimer.doGet ************ ");
           
        } catch (NamingException e) {  
            e.printStackTrace();  
        }  
    }  
}
