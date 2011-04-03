package com.eni.dvtejb.client;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Properties;

import javax.naming.InitialContext;

import com.eni.dvtejb.metier.sessions.PanierCycle;

// Cette classe fonctionne en dehors du container EJB (JBoss)
public class ClientStandalone {
	
	public static void main(String[] args) throws Exception
	   {
		
		  Properties proprietes = new Properties();  
		  proprietes.load(new FileInputStream("jndi.properties")); 
	      InitialContext ctx = new InitialContext(proprietes);
	      PanierCycle panier = (PanierCycle) ctx.lookup("VenteEnLigne/PanierCycleBean/remote");
	    
	      panier.acheter("DVD", 1);
	      panier.acheter("DVD", 1);
	      panier.acheter("souris", 1);

	      HashMap<String, Integer> panierMap = panier.getContenuPanier();
	      for (String article : panierMap.keySet())
	      {
	         System.out.println(panierMap.get(article) + "     " + article);
	      }

	      panier.payer();

	      try
	      {
	    	  panier.getContenuPanier();
	      }
	      catch (javax.ejb.NoSuchEJBException e)
	      {
	         System.out.println("===> Exception catchée.");
	      }
	      
	      System.out.println("===> Création d'une seconde instance de bean pour tester la suppression dû au timeout");
	      panier = (PanierCycle) ctx.lookup("VenteEnLigne/PanierCycleBean/remote");
	      panier.acheter("DVD", 1);
	      
	      System.out.println("===> Pause de 30 secondes. C'est aussi 30 secondes d'inactivité.");
	      Thread.sleep(25 * 1000);
	      try
	      {
	    	  System.out.println("===> Achat d'un autre DVD");
	    	  panier.acheter("DVD", 1);
	      } catch (javax.ejb.NoSuchEJBException e)
	      {
	    	  System.out.println("===> Le bean a été supprimé");
	      }
	   }
}