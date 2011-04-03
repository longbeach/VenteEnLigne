package com.eni.dvtejb.metier.sessions;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.jboss.ejb3.annotation.SecurityDomain;

import com.eni.dvtejb.metier.entities.Adresse;
import com.eni.dvtejb.metier.entities.Client;
import com.eni.dvtejb.metier.entities.Utilisateur;

//venteEnLigne_domaine_DB : domaine de sécurité pour authentication /authentification via la base de données
//venteEnLigne_domaine : domaine de sécurité pour authentication /authentification via venteEnLigne-users.properties et venteEnLigne-roles.properties
//venteEnLigne_domaine_LDAP : domaine de sécurité pour authentication /authentification via LDAP
@SecurityDomain("venteEnLigne_domaine_DB") 
@RolesAllowed({"A", "G"})
// @DeclareRoles doit être utilisé quand on fait usage de isCallerInRole
//@DeclareRoles( {"A", "G"})
@Stateful
public class UtilisateurDAOBean  implements UtilisateurDAOBeanRemote {
	
	private static final Logger log = Logger.getLogger(UtilisateurDAOBean.class);
	
	@PersistenceContext
	private EntityManager em;
	
	@RolesAllowed({"A", "G"}) // Administrateurs et gestionnaires sont habilités à lister les utilisateurs
	public List<Utilisateur> rechercherTous() {
		log.info("Debut methode rechercherTous()" );
		   
		Query query = em.createQuery("select u from Utilisateur u where type_util = 'C'");
        return query.getResultList();
    }
	
	@RolesAllowed({"A"})  // Seuls les administrateurs sont habilités à modifier des utilisateurs
	public void sauver(Utilisateur utilisateur) {
	   log.info("Debut methode sauver() ----------------------------------" );
		 
	   if (null == utilisateur) {
		   log.info("utilisateur est nul");
	   } else {
		   log.info("utilisateur n'est pas null ");
	   }
       Long utilisateurId = utilisateur.getUtilisateurid();
       log.info("utilisateurId vaut : " + utilisateurId);                     
       		
        if (null == utilisateurId.toString() || 0 == utilisateurId ) {
            // nouveau
            em.persist(utilisateur);
        } else {
            // modification   
        	Utilisateur util2 = em.find(Utilisateur.class, utilisateurId);
        	Adresse adresse = util2.getAdresseFk();
        	  log.info("Avant merge adresse");
        	  if (null == adresse) {
        		  log.info("adresse est null");
        	  }
        	utilisateur.setAdresseFk(adresse);
            em.merge(utilisateur);
        }
    }

	@RolesAllowed({"A"}) // Seuls les administrateurs sont habilités à supprimer des utilisateurs
	public void supprimer(long utilisateurid) {
		 log.info("Debut methode supprimer()" );
	
		 log.info("utilisateurid vaut : " + utilisateurid );
		Utilisateur utilisateur = rechercher(utilisateurid);
        if (utilisateur != null) {
       	 log.info("Utilisateur trouve !" );
       	 Adresse adresse = utilisateur.getAdresseFk();      
       	 if (null != adresse) {
     	    em.remove(adresse);
       	 }       
            em.remove(utilisateur);
        }
    }

	@RolesAllowed({"A", "G"}) // Administrateurs et gestionnaires sont habilités à lister les utilisateurs
	public Utilisateur rechercher(long utilisateurid) {
		 log.info("Debut methode rechercher()" );
		 
		 if (null != em) {
			 log.info("em n'est pas nul" );
		 }
		 
		 Client cl = em.find(Client.class, utilisateurid);
		 if (null != cl) {
			 log.info("cl n'est pas nul" );
		 }
		 
		 Utilisateur ut = null;
		 ut = em.find(Utilisateur.class, utilisateurid);
		 if (null != ut) {
			 log.info("ut n'est pas nul" );
		 }
        return ut;
    }
}
