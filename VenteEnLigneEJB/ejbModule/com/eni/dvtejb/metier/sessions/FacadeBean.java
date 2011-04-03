package com.eni.dvtejb.metier.sessions;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.eni.dvtejb.metier.dtos.MagasinDTO;
import com.eni.dvtejb.metier.entities.Adresse;
import com.eni.dvtejb.metier.entities.Client;
import com.eni.dvtejb.metier.entities.Utilisateur;

@Stateful
@Remote (FacadeRemote.class)
public class FacadeBean implements FacadeRemote,  Serializable{
	
	private static final long serialVersionUID = 1L;	
	private static final Logger log = Logger.getLogger(FacadeBean.class);

	@PersistenceContext(unitName = "VenteEnLigneClientJavaEE", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;
	
   public Utilisateur findUtilisateurByLoginPwd(String login, String pwd){
		
		Query query = entityManager.createQuery("SELECT u FROM Utilisateur u WHERE" +
				" login = ?1 AND password = ?2");
		query.setParameter(1, login);
		query.setParameter(2, pwd);
		Object object;
		try { 
		   object = query.getSingleResult();
		} catch (NoResultException e) { 
			object = null; 
		}
					
		if ( null != object ){
			Utilisateur utilisateur = (Utilisateur)object;
			return utilisateur;
		} else {
			return null;
		}			
	}
   
   public Utilisateur findUtilisateurById(long utilisateurId){
		
		Query query = entityManager.createQuery("SELECT u FROM Utilisateur u WHERE" +
				" utilisateurid = ?1");
		query.setParameter(1, utilisateurId);
		Object object;
		try { 
		   object = query.getSingleResult();
		} catch (NoResultException e) { 
			object = null; 
		}
					
		if ( null != object ){
			Utilisateur utilisateur = (Utilisateur)object;
			return utilisateur;
		} else {
			return null;
		}			
	}
   
   public Adresse findAdresseById(long adresseId){
		
		Query query = entityManager.createQuery("SELECT a FROM Adresse a WHERE" +
				" adresseid = ?1");
		query.setParameter(1, adresseId);
		Object object;
		try { 
		   object = query.getSingleResult();
		} catch (NoResultException e) { 
			object = null; 
		}
					
		if ( null != object ){
			Adresse adresse = (Adresse)object;
			return adresse;
		} else {
			return null;
		}			
	}
   
   public boolean updateClient(Client client, Adresse adresse){
	   
	   if (entityManager.find(Client.class, client.getUtilisateurid()) == null) {
		   throw new RuntimeException("Impossible de mettre à jour le client car il n'y a pas d'ancienne version");
		   }
	  
	   if (entityManager.find(Adresse.class, adresse.getAdresseid()) == null) {
		   throw new RuntimeException("Impossible de mettre à jour l'adresse car il n'y a pas d'ancienne version");
		   }
	   client.setAdresseFk(adresse);
	   entityManager.merge(client);  
	   
	   return true;
   }
   
	public List findMagasin(){
		return entityManager.createQuery("select c.nom, p.nom, a.nom, a.articleid, a.prix, s.quantite  from Catalogue c, Produit p, Article a, Stock s" +
				" where c.catalogueid = p.catalogueFk" +
				" and p.produitid = a.produitFk" +
				" and a.stockFK = s.stockid").getResultList();		
	}
	
	// Methode inutilisée
	public List<MagasinDTO> findBoutique(){
		log.info("Lancement de la methode findBoutique()");
		String requete = "SELECT NEW com.eni.dvtejb.metier.dtos.MagasinDTO( c.nom, p.nom, a.nom, a.articleid, a.prix, s.quantite) " +
		              " FROM Catalogue c, Produit p, Article a, Stock s" + 
		       		  " WHERE c.catalogueid = p.catalogueFk" +
					  " AND p.produitid = a.produitFk" +
					  " AND a.stockFK = s.stockid";					
		return entityManager.createQuery(requete).getResultList();		
	}
	
	@SuppressWarnings("unchecked") 
	public List<MagasinDTO> findBoutiqueIntervalle (int max, int premier){
		log.info("Lancement de la methode findBoutiqueIntervalle()");
		String requete = "SELECT NEW com.eni.dvtejb.metier.dtos.MagasinDTO( c.nom, p.nom, a.nom, a.articleid, a.prix, s.quantite) " +
		              " FROM Catalogue c, Produit p, Article a, Stock s" + 
		       		  " WHERE c.catalogueid = p.catalogueFk" +
					  " AND p.produitid = a.produitFk" +
					  " AND a.stockFK = s.stockid";		
		Query req= entityManager.createQuery(requete);
		req.setMaxResults(max);
		req.setFirstResult(premier);		
		return req.getResultList();		
	}
	
	  public int getNombreTotalArticles() {
			String requete = "SELECT count(a) " +
            " FROM Catalogue c, Produit p, Article a, Stock s" + 
     		  " WHERE c.catalogueid = p.catalogueFk" +
			  " AND p.produitid = a.produitFk" +
			  " AND a.stockFK = s.stockid";	
	        return ((Long) entityManager.createQuery(requete).getSingleResult()).intValue();
	    }
	
	public String findMdpByLoginEmail(String login, String email) {
		log.info("Lancement de la methode findMdpByLoginEmail()");
		String mdp = null;
		Query reqPosition = entityManager.createQuery("SELECT c.password " +
		              " FROM Client c" + 
		       		  " WHERE c.login = ?1 " +
					  " AND c.email = ?2 ");
		
		reqPosition.setParameter(1, login);
		reqPosition.setParameter(2, email);

		log.info("Avant req");
		String lemdp = (String)reqPosition.getSingleResult();
		log.info("mdp vaut : " + lemdp);
		mdp = lemdp;

		return mdp;				
	}
	
	public String testPourFlex() {
		log.info("Appel de  testPourFlex");
		System.out.println("Appel de  testPourFlex ...");
		return "marche";
	}
}
