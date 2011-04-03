package com.eni.dvtejb.tests;

import java.io.FileInputStream;
import java.util.Properties;

import javax.ejb.EJBAccessException;
import javax.naming.InitialContext;

import junit.framework.TestCase;

import org.jboss.security.client.SecurityClient;
import org.jboss.security.client.SecurityClientFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.eni.dvtejb.metier.entities.Client;
import com.eni.dvtejb.metier.sessions.GererUtilisateur;

public class GererUtilisateurTest  extends TestCase {
    
    public GererUtilisateurTest() {
        super();
    }

	@Before
	public void setUp() throws Exception {
		
		try { 
		SecurityClient securityClient = SecurityClientFactory.getSecurityClient();	
		System.out.println("Avant login");		
		securityClient.setSimple("log1", "pwd1");		
		securityClient.login(); 
		System.out.println("Après login");		
		} catch (Exception e) {
			e.printStackTrace();
			} 			
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAjoutUtilisateur() {
		System.out.println("Debut  ajoutUtilisateurTest");
		  try {
			  
		  Properties proprietes = new Properties();  
		  proprietes.load(new FileInputStream("jndi.properties")); 
		  
		 // proprietes.setProperty(Context.SECURITY_PRINCIPAL, "azerty");
		 // proprietes.setProperty(Context.SECURITY_CREDENTIALS, "qwerty");

	      InitialContext ctx = new InitialContext(proprietes);
	   
	      GererUtilisateur gererUtil = (GererUtilisateur) ctx.lookup("VenteEnLigne/GererUtilisateurBean/remote");
	      Client client = new Client();	      
	      
	      gererUtil.ajouter(client);	      

		  } catch (EJBAccessException ex)
	      {
	         System.out.println("Erreur attendue de type EJBAccessException: " + ex.getMessage());
	      
		  }catch (Exception ex) {
	           
	            ex.printStackTrace();
	            fail("Exception pendant le test ajoutUtilisateurTest");
	        }
	}
}
