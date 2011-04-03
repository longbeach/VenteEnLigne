package com.eni.dvtejb.metier.sessions;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.interceptor.Interceptors;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.jboss.logging.Logger;

import com.eni.dvtejb.metier.dtos.CommandeDTO;
import com.eni.dvtejb.metier.entities.Adresse;
import com.eni.dvtejb.metier.entities.Article;
import com.eni.dvtejb.metier.entities.Client;
import com.eni.dvtejb.metier.entities.Commande;
import com.eni.dvtejb.metier.entities.Lignecommande;
import com.eni.dvtejb.metier.entities.Utilisateur;
import com.eni.dvtejb.metier.services.ArticlePanier;
import com.eni.dvtjeb.metier.interceptors.CommanderInterceptor;

@Stateful
@Remote (PanierBeanRemote.class)
public class PanierBean implements PanierBeanRemote, Serializable {

	private static final Logger log = Logger.getLogger(PanierBean.class);
	
	@PersistenceContext(unitName="VenteEnLigneModuleEJB")
	EntityManager em;
	
	private static final long serialVersionUID = 1L;
		
	private ArrayList<ArticlePanier> articles;
	
	// Initialisation de la liste d'articles
	@PostConstruct
	public void creation(){
		articles = new ArrayList<ArticlePanier>();
	}

	/**
     * Constructeur par défaut. 
     */
    public PanierBean() {
    }

	public Commande genererCommande(Article[] listArticles, Client client, BigDecimal[] quantites) {
		
		// A chaque article (prix, nom) est associé une ligne de commande (quantite)
		List<Lignecommande> ligneCommandes = new ArrayList<Lignecommande> (listArticles.length);
				
	   //	entityManager.getTransaction().begin();		
		for (int i=0;i<listArticles.length;i++){
			
			Lignecommande lignecommande = new Lignecommande();
			lignecommande.setArticleFk(listArticles[i]);
			lignecommande.setQuantite(quantites[i]);
			ligneCommandes.add(lignecommande);
		}
	
		// Une commande est composée de 1 à N lignes de commande
		Commande commande = new Commande();
		commande.setUtilisateurFk(client);
		
        Date aujourdhui = new Date();
        long t = aujourdhui.getTime();
        java.sql.Date aujourdhuiSQL =  new java.sql.Date(t);
		commande.setDatecommande( aujourdhuiSQL);
		
		Set<Lignecommande> set = new HashSet<Lignecommande>();
		set.addAll( ligneCommandes );

		commande.setLignecommandeCollection(set);
		
		//entityManager.persist(commande);
       // entityManager.getTransaction().commit();
		
		return commande;
	}

	//@Override
	public Collection<String> getProduits() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Article findById(long id){
		return em.find(Article.class, id);
	}
	
	public void viderPanier() {
	 articles.clear();
	}
	
	//@Override
	public void ajouterArticle(Article article, BigDecimal quantite) {
		articles.add( new ArticlePanier(article, quantite));		    
	}
	
	//@Override
	public void supprimerArticle(ArticlePanier articlePanier) {
		ArticlePanier art = new ArticlePanier();
		long artId =  articlePanier.getArticle().getArticleid();
		for (Iterator it = articles.iterator (); it.hasNext (); ) {
			 art = (ArticlePanier)it.next ();
			if (art.getArticle().getArticleid() == artId){
				break;
			}
		  }
		articles.remove(art);	
		return;
	}

	public ArrayList<ArticlePanier> getPanier() {
		return articles;
	}
	
	public double getMontantTotal() {
		
		double total=0;
		if(articles==null||articles.size()==0) {
			return total;
		}

		for(ArticlePanier articlePanier:articles) {
			double quantiteArticle = 0;
			try{
				quantiteArticle = articlePanier.getQuantite().doubleValue();
			}
			catch (NumberFormatException nfe){
				System.out.println("NumberFormatException: " + nfe.getMessage());
			}

			double totalArticle = articlePanier.getArticle().getPrix() * quantiteArticle;
			total+=totalArticle;
		}
			return total;
	}
	
	public List afficherHistoCommandes(Utilisateur u) {
				
		Query requete = em.createNativeQuery("select c.CommandeID,  a.nom, c.DATECOMMANDE "+
				"from Commande c, Utilisateur u, Lignecommande_tj tj, lignecommande lc, article a "+
				"where c.UTILISATEUR_FK = u.UTILISATEURID "+
				"and c.COMMANDEID = tj.COMMANDE_FK "+
				"and lc.LIGNECOMMANDEID = tj.LIGNECOMMANDE_FK "+
				"and lc.ARTICLE_FK = a.ARTICLEID "+
				"and u.UTILISATEURID = ?1 ");
		
		requete.setParameter(1, u.getUtilisateurid());
		
		List listeCommandes = requete.getResultList();
		return listeCommandes;		
	}
	
	@Interceptors(CommanderInterceptor.class)
	public void commander(Client client, ArrayList<ArticlePanier> articlesPanier, String numCC, String typeCC, java.sql.Date expirationDate){
		
		log.info("Debut methode commander");

		Client cl = em.find(Client.class, client.getUtilisateurid());
		Adresse adresse = em.find(Adresse.class, 1L);
	    Commande commande = new Commande();
	    
	    ArrayList<Lignecommande> lignesCommande = new ArrayList<Lignecommande>();

	    log.info("Traitement des articles du panier");
	   	ArticlePanier artPanier = new ArticlePanier();
	   	for (Iterator it = articlesPanier.iterator (); it.hasNext (); ) {
	   		
	   		artPanier = (ArticlePanier)it.next ();
			Lignecommande lc = new Lignecommande();
			lc.setArticleFk(artPanier.getArticle());
			lc.setQuantite(artPanier.getQuantite());
			em.persist(lc);
			lignesCommande.add(lc);			  
		  } 
		 
	   	Set lignesCommandeSet = new HashSet(lignesCommande);
	   
	   	commande.setAdresseFk(adresse);
	   	commande.setUtilisateurFk(cl);
	   	
	    Date aujourdhui = new Date();
        long t = aujourdhui.getTime();
        java.sql.Date aujourdhuiSQL = new java.sql.Date(t);
        commande.setDatecommande(aujourdhuiSQL);
        commande.setDateExpirationCartecredit(expirationDate);
        commande.setNumeroCartecredit(numCC);
        commande.setTypeCartecredit(typeCC);
	   	commande.setLignecommandeCollection(lignesCommandeSet); 	
	 
	   	log.info("Insertion de la commande");
	   em.persist(commande);	 
	   	   
	   // Envoi de mail de façon asynchrone
	   producteurMail(commande);
	   log.info("Fin methode commander");
	}
	
	
	@Resource(mappedName ="ConnectionFactory")
	private ConnectionFactory connectionFactory;
	
	@Resource(mappedName="topic/MailConfirmationMdbTopic")
	private Topic leTopic;
	
	private void producteurMail(Commande commande){
		log.info("Debut de la méthode producteurMail");
		Connection conn = null;
		Session session = null;
		
		try {
			 conn = connectionFactory.createConnection();			 
			 session = conn.createSession(false,Session.AUTO_ACKNOWLEDGE);				 
			 MessageProducer producteur = session.createProducer(leTopic);			 
			 ObjectMessage message = session.createObjectMessage();
			 message.setObject(commande);
			 producteur.send(message);
			 
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			try {
				log.info("Fin de la méthode producteurMail");
				session.close();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}						
	}	
	
	/*
	 * (non-Javadoc)
	 * @see com.eni.dvtejb.metier.sessions.PanierBeanRemote#afficherCommandesPrecedentes(com.eni.dvtejb.metier.entities.Utilisateur)
	 * query.getResultList() retourne une version de List qui n'est pas générique ==> utilisation de l'annotation SuppressWarnings 
	 * pour supprimer le warning.
	 * Méthode JSF 2.
	 */
	@SuppressWarnings("unchecked") 
	public List<CommandeDTO> afficherCommandesPrecedentes (Utilisateur u) {
		List<CommandeDTO> listeCommandeDTO = new ArrayList<CommandeDTO>();

		Query requete = em.createNativeQuery("SELECT c.CommandeID, a.nom, c.DATECOMMANDE "+
				" FROM Commande c, Utilisateur u, Lignecommande_tj tj, lignecommande lc, article a "+
				" WHERE c.UTILISATEUR_FK = u.UTILISATEURID "+
				" AND c.COMMANDEID = tj.COMMANDE_FK "+
				" AND lc.LIGNECOMMANDEID = tj.LIGNECOMMANDE_FK "+
				" AND lc.ARTICLE_FK = a.ARTICLEID "+
				" AND u.UTILISATEURID = ?1 ");
		
		requete.setParameter(1, u.getUtilisateurid());		
		List listeCommandes = requete.getResultList();
		
		int taille  = listeCommandes.size();
		for (int i = 0; i<taille;i++){
		   CommandeDTO  commandeDTO = new CommandeDTO();
    	   Object[] ligne = (Object[])listeCommandes.get(i);
    	   BigDecimal  commandeId = (BigDecimal)ligne[0]; 
    	   // cast BigDecimal vers long
    	   long a =  commandeId.longValue();
    	   commandeDTO.setCommandeId(a) ;
    	   String nomArticle = (String)ligne[1]; 
    	   commandeDTO.setNomArticle(nomArticle) ;
    	   Date dateCommande = (Date)ligne[2]; 
    	   commandeDTO.setDateCommande(dateCommande) ;
    	   listeCommandeDTO.add(commandeDTO);
		}
	
		return listeCommandeDTO;		
	}
}
