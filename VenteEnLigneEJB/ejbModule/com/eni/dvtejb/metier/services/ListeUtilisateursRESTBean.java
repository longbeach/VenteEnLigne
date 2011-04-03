package com.eni.dvtejb.metier.services;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.eni.dvtejb.metier.entities.Adresse;
import com.eni.dvtejb.metier.entities.Utilisateur;

@Path("/AfficherListeUtilisateurs")
@Produces({"application/xml", "application/json"})
@Stateless
@TransactionManagement(javax.ejb.TransactionManagementType.BEAN)
public class ListeUtilisateursRESTBean implements ListeUtilisateursRESTRemote {
	
	private static final Logger log = Logger.getLogger(ListeUtilisateursRESTBean.class);	
	private EntityManagerFactory emFactory;
	private EntityManager em;


	@PostConstruct
	public void creerEntityManager() {
		log.info("Debut méthode creerEntityManager()");		
		emFactory = Persistence.createEntityManagerFactory("VenteEnLigneModuleEJB");
        em = emFactory.createEntityManager();	
	}
	
	@PreDestroy
	public void fermerEntityManager() {
		em.close();	
	}
		  	 
	@GET
	public ListeNomsUtilisateurs ListerUtilisateurs() {
		
		log.info("Debut méthode ListerUtilisateurs()");
          
		if (null == em) {
			log.info("em est nul.");
		}
		
     	 Query query = em.createNativeQuery("Select u.nom from Utilisateur u");     	 
		 List<String> nomsutilisateurs = query.getResultList();
		 if (null != nomsutilisateurs) {
			 log.info("nomsutilisateurs n'est pas nul.");
			}	
		 
		 ListeNomsUtilisateurs liste = new ListeNomsUtilisateurs();
		 
		 for (String u : nomsutilisateurs){
			 liste.nomsUtilisateurs.add(u); 
		 }
		 		 		 
		 log.info("Fin méthode ListerUtilisateurs()");
		 return liste;
   }
	
	@GET
	@Path("/annuaire")
	@Produces("text/html")
	public String getPrenomUtilisateur(@DefaultValue("Bock")  @QueryParam("nomUtilisateur") String nom) {
		
		log.info("Debut méthode getPrenomUtilisateur(String nom)");
          
		if (null == em) {
			log.info("em est nul.");
		}
		
     	Query query = em.createNativeQuery("Select u.prenom from Utilisateur u where u.nom = ?1");     
     	query.setParameter(1, nom);
		String prenom = (String)query.getSingleResult();
		 	 				 		 		
		log.info("Fin méthode getPrenomUtilisateur(String nom)");
		String retour = "Le prenom de l'utilisateur est : " + prenom;
		return retour;
   }
		
	/*
	 * Méthode avec transactions.
	 * (non-Javadoc)
	 * @see com.eni.dvtejb.metier.services.ListeUtilisateursRESTRemote#putNumeroRue(java.lang.Long, java.math.BigDecimal)
	 */
	@POST
	@Path("MAJ")
	@Produces("text/html")
	public Response changeNumeroRue( @QueryParam("adId") @DefaultValue("0") Long adresseId, 
			@QueryParam("numRue") @DefaultValue("22") BigDecimal numeroRue) {
			 
		log.info("Debut méthode changeNumeroRue(long adresseId, BigDecimal numeroRue) avec POST");       		
			
	    try { 
	    	 EntityManagerFactory emFactory1;
	    	 EntityManager em1;
	    	 	 	   	  
	    	 UserTransaction utx1 = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
	    	 log.info("Avant begin");
	    	 utx1.begin();
	    	 
	    	 emFactory1 = Persistence.createEntityManagerFactory("VenteEnLigneModuleEJB");
	    	 em1 = emFactory1.createEntityManager();
	    	 
	    	 Adresse adresse = em1.find(Adresse.class, adresseId);			    
			 adresse.setNumero(numeroRue);

 			 em1.persist(adresse);
			 log.info("Apres persist");
			 em1.close();

			 utx1.commit();		
			
	    } catch (Exception e) {  
            e.printStackTrace();  
        } 		
		 		 		 
		log.info("Fin méthode changeNumeroRue(long adresseId, BigDecimal numeroRue)");		

		 Response response = Response.status(200).build(); //update
		 return response;
   }
	
	@POST
	@Path("/{nomArt}")
	@Produces("text/html")
	public String recupererPrixArticle ( @PathParam("nomArt") @DefaultValue("Un") String nomArticle) {
			 
		log.info("Debut méthode recupererPrixArticle POST");
		if (null == em) {
			log.info("em est nul.");
		}
		
     	Query query = em.createNativeQuery("Select a.prix from Article a where a.nom = ?1");     
     	query.setParameter(1, nomArticle);
     	BigDecimal prix = (BigDecimal)query.getSingleResult();
		 	 				 		 		
		log.info("Fin méthode getPrenomUtilisateur(String nom)");
		String retour = "Le prix de l'article est : " + prix;
		return retour;
   }
	
	@PUT
	@Path("MAJPWD/{idU}/{pwd}")
	@Produces("text/html")
	public Response updaterPwd( @PathParam("idU") Long idUtilisateur, @PathParam("pwd")String pwd)
	{
		log.info("Debut méthode updatePassword POST");
		try { 
	    	 EntityManagerFactory emFactory2;
	    	 EntityManager em2;
	    	 	 	   	  
	    	 UserTransaction utx2 = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
	    	 log.info("Avant begin");
	    	 utx2.begin();
	    	 
	    	 emFactory2 = Persistence.createEntityManagerFactory("VenteEnLigneModuleEJB");
	    	 em2 = emFactory2.createEntityManager();
	    	 
	    	 Utilisateur u = em2.find(Utilisateur.class, idUtilisateur);			    
			 u.setPassword(pwd);

			 em2.persist(u);
			 log.info("Apres persist");
			 em2.close();

			 utx2.commit();		
			
	    } catch (Exception e) {  
           e.printStackTrace();  
       } 		
		log.info("Fin méthode updatePassword ");		

		 Response response = Response.status(200).build(); //update
		 return response;
	}
	
}
