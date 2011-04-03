package com.eni.dvtejb.tests;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.Properties;

import javax.ejb.EJBAccessException;
import javax.naming.InitialContext;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.jboss.security.client.SecurityClient;
import org.jboss.security.client.SecurityClientFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.eni.dvtejb.metier.entities.Client;
import com.eni.dvtejb.metier.sessions.UtilisateurDAOBeanRemote;

public class UtilisateurDAOBeanTest   extends TestCase {
	
	private static final Logger log = Logger.getLogger(UtilisateurDAOBeanTest.class);
	
	SecurityClient securityClient;
    UtilisateurDAOBeanRemote utilisateurDAO;
	
    public UtilisateurDAOBeanTest() {
        super();
    }

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		try { 
			  securityClient = SecurityClientFactory.getSecurityClient();
			  Properties proprietes = new Properties();  
			  proprietes.load(new FileInputStream("jndi.properties")); 			  
		      InitialContext ctx = new InitialContext(proprietes);	
		      utilisateurDAO = (UtilisateurDAOBeanRemote) ctx.lookup("VenteEnLigne/UtilisateurDAOBean/remote");
		} catch (Exception e) {
				e.printStackTrace();
			} 		
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testAjoutUtilisateurParClient() {
		System.out.println("Debut testAjoutUtilisateurParClient");

		  try {
			    // Pour authentication /authentification via venteEnLigne-users.properties et venteEnLigne-roles.properties
			    // Jacques a le rôle Client
				//securityClient.setSimple("Jacques", "JacquesMDP");
				// Pour authentication /authentification via la base de données
			    // securityClient.setSimple("log1", "pwd1");
			    // Pour authentication /authentification via LDAP

			    securityClient.setSimple("log1", "pwd1");			    			 
				securityClient.login();
				   				
				System.out.println("apres login");				
																							  
		      Client client = new Client();
		      // setters sur l'entite Client ...	      
	     	utilisateurDAO.sauver(client);	      

		  } catch (EJBAccessException ex)
	      {
			  System.out.println("Erreur attendue de type EJBAccessException: " + ex.getMessage());	         
	      
		  }catch (Exception ex) {
	           
	            ex.printStackTrace();
	            fail("Exception pendant le test testAjoutUtilisateurParClient");
	        }
		  System.out.println("Fin  testAjoutUtilisateurParClient");
	}
	
	@Test
	public void testAjoutUtilisateurParGestionnaire() {
		System.out.println("Debut  testAjoutUtilisateurParGestionnaire");

		  try {
   			    // Pour authentication /authentification via venteEnLigne-users.properties et venteEnLigne-roles.properties  
			    // Robert a le rôle Gestionnaire			
				//securityClient.setSimple("Robert", "RobertMDP");
				// Pour authentication /authentification via la base de données
			    securityClient.setSimple("log7", "pwd7");
				securityClient.login(); 
			  
		      Client client = new Client();
		      // setters sur l'entite Client	         
	     	utilisateurDAO.sauver(client);	      

		  } catch (EJBAccessException ex)
	      {
			  System.out.println("Erreur attendue de type EJBAccessException: " + ex.getMessage());	         
	      
		  }catch (Exception ex) {
	           
	            ex.printStackTrace();
	            fail("Exception pendant le test testAjoutUtilisateurParGestionnaire");
	        }
		  System.out.println("Fin  testAjoutUtilisateurParGestionnaire");
	}
	
	@Test
	public void testAjoutUtilisateurParAdministrateur() {
		System.out.println("Debut  testAjoutUtilisateurParAdministrateur");

		  try {
			    // Pour authentication /authentification via venteEnLigne-users.properties et venteEnLigne-roles.properties
			    // Irene a le rôle Administrateur
				//securityClient.setSimple("Irene", "IreneMDP");
				// Pour authentication /authentification via la base de données
				securityClient.setSimple("log6", "pwd6");
				securityClient.login(); 				
			  
		      Client client = new Client();
		      BigDecimal fax = new BigDecimal("0143545654");
		      client.setFax(fax);
		      BigDecimal telephone = new BigDecimal("0134345455");
		      client.setTelephone(telephone);	      		      
	     	  utilisateurDAO.sauver(client);	      

		  } catch (EJBAccessException ex)
	      {
			  System.out.println("Erreur attendue de type EJBAccessException: " + ex.getMessage());	         
	      
		  }catch (Exception ex) {
	           
	            ex.printStackTrace();
	            fail("Exception pendant le test testAjoutUtilisateurParAdministrateur");
	        }
		  System.out.println("Fin  testAjoutUtilisateurParAdministrateur");
	}
	
	@Test
	public void testAjoutUtilisateurParInconnu() {
		System.out.println("Debut  testAjoutUtilisateurParInconnu");

		  try {
			    // toto n'a aucun rôle
				securityClient.setSimple("toto", "toto");
				securityClient.login(); 				
			  
		      Client client = new Client();     		      
	     	  utilisateurDAO.sauver(client);	      

		  } catch (EJBAccessException ex)
	      {
			  System.out.println("Erreur attendue de type EJBAccessException: " + ex.getMessage());	         
	      
		  }catch (Exception ex) {
	           
	            ex.printStackTrace();
	            fail("Exception pendant le test testAjoutUtilisateurParInconnu");
	        }
		  System.out.println("Fin  testAjoutUtilisateurParInconnu");
	}
}
