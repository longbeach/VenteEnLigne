package com.eni.dvtejb.metier.sessions;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.eni.dvtejb.metier.entities.Client;
import com.eni.dvtejb.metier.entities.Commande;

@Stateless
public class CommandeDAOImpl  implements CommandeDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void traitementCarteCredit() {
		
		 Date aujourdhui = new Date();
         long t = aujourdhui.getTime();
         java.sql.Date aujourdhuiSQL =  new java.sql.Date(t);
     		
		List commandes = em.createQuery("from Commande").getResultList();		
		Iterator iter = commandes.iterator();
		
		while (iter.hasNext()) {
		  Commande commande = (Commande) iter.next();
		  
		  if (commande.getDateExpirationCartecredit().before(aujourdhuiSQL)) {
			  			  
			  try {				  
				  InitialContext initialContext = new InitialContext(); 
				   ClientDAO clientDAO = (ClientDAO) initialContext.lookup("VenteEnLigne/ClientDAOImpl/remote");  
				   Client client = clientDAO.findById(commande.getUtilisateurFk().getUtilisateurid());
			       System.out.println("************************  CARTE DE CREDIT EXPIREE ! ***************************************");
				   System.out.println("******* Envoi d'email d'avertissement au client :" + client.getNom() + " " + client.getPrenom());
				   System.out.println("******* Numéro de carte de credit: " + commande.getNumeroCartecredit());
				   System.out.println("******* Date d'expiration : "  + commande.getDateExpirationCartecredit());
				   System.out.println("************************  CARTE DE CREDIT EXPIREE ! ***************************************");
			} catch (NamingException e) {
				
			}
		  } // Fin If 
		} // Fin boucle while
	}
}
