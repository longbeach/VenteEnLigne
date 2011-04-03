package com.eni.dvtejb.client;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import junit.framework.TestCase;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.eni.dvtejb.metier.entities.Article;
import com.eni.dvtejb.metier.entities.Client;
import com.eni.dvtejb.metier.entities.Commande;
import com.eni.dvtejb.metier.sessions.ClientDAO;

// Utilise persistence.xml non JTA
public class ClientDAOTest extends TestCase {

	private static Logger logger = Logger.getLogger(ClientDAOTest.class.getName());
	
    private EntityManagerFactory emFactory;
    private EntityManager em;  
    
    public static final String INSERE_CATALOGUE = 
  	  "Insert into CATALOGUE (CATALOGUEID, DESCRIPTION, NOM) Values (catalogue_seq.nextval, ?1, ?2)";

    public ClientDAOTest(String testName) {
        super(testName);
    }

    @BeforeClass 
    protected void setUp() throws Exception {
        super.setUp();

        try {
            logger.info("JPA EntityManager pour tests unitaires ");
            emFactory = Persistence.createEntityManagerFactory("VenteEnLigneClientJavaSE");
            em = emFactory.createEntityManager();
        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Exception pendant l'instantiation du JPA EntityManager.");
        }
    }

    @AfterClass 
    protected void tearDown() throws Exception {
        super.tearDown();
        logger.info("Fermeture de la couche Hibernate JPA.");
        if (em != null) {
            em.close();
        }
        if (emFactory != null) {
            emFactory.close();
        }
    }

    @Test 
    public void testPersistence() {
        try {
        	logger.info("debut de testPersistence()");
        
        	  Properties proprietes = new Properties();  
    		  proprietes.load(new FileInputStream("jndi.properties")); 

    	      InitialContext ctx = new InitialContext(proprietes);
    	      ClientDAO clientDAO = (ClientDAO) ctx.lookup("VenteEnLigne/ClientDAOImpl/remote");
          	      
    	    
    	      //Appel d'une requête nommée via un stateless session bean 
    	      List<Client> lesNoms = clientDAO.findByPrenom("Max");
    	      System.out.println("Appel d'une requête nommée via un stateless session bean");
    	      for (Client p : lesNoms) {
    	          System.out.println(p.getNom());
    	        }
    	 
    	      final int nombreClientTrouve = 
    	    	  em.createQuery(" from Client c").getResultList().size();
    	      assertEquals(5, nombreClientTrouve);    	   
  
    	      
    	      Query reqParametree =  em.createQuery("Select c from Client c where nom = :nomParam");
    	      reqParametree.setParameter("nomParam", "Bock");
    	      List<Client> resultats = reqParametree.getResultList();
    	      for (Client p : resultats) {
    	          System.out.println(p.getPrenom());
    	        }
    	      
    	      Query reqLettre =  em.createQuery("Select c from Client c where nom like 'B%'");
    	      List<Client> resultatsreqLettre = reqLettre.getResultList();
    	      for (Client p : resultatsreqLettre) {
    	          System.out.println(p.getNom());
    	        }
    	      
    	      Query reqJoin = em.createQuery("Select a FROM Article a join a.produitFk prod" +
    	      		" where prod.nom = 'Foot'" );
    	      List<Article> resultatsreqJoin = reqJoin.getResultList();
    	      for (Article a : resultatsreqJoin) {
    	          System.out.println(a.getNom());
    	        }
    	      
    	      Query reqDistinct = em.createQuery("Select distinct co.typeCartecredit FROM Commande co " );
    	      List<String> typesCarteCredit = reqDistinct.getResultList();
    	      for (String typeCarteCredit : typesCarteCredit) {
    	          System.out.println(typeCarteCredit);
    	        }
    	      
    	      Query reqInnerJoin = em.createQuery("Select c FROM Client c INNER JOIN c.adresseFk ad WHERE c.titre = 'Mr' AND ad.codepostal > 77000" );
    	      List<Client> clients = reqInnerJoin.getResultList();
    	      for (Client client : clients) {
    	          System.out.println(client.getNom());
    	      }
    	   
    	      Query reqLeftJoin = em.createQuery("SELECT co FROM Commande co LEFT JOIN co.utilisateurFk c WHERE c.utilisateurid > 2" );
    	      List<Commande> commandes = reqLeftJoin.getResultList();
    	      for (Commande commande : commandes) {
    	          System.out.println(commande.getCommandeid());
    	        }
    	        
    	      // Sous-requête 
    	      Query reqSousReq = em.createQuery("SELECT distinct co FROM Commande co where EXISTS " +
    	      		" ( select cl  from co.utilisateurFk cl where cl.nom ='Gatersa' )");
    	      List<Commande> listeCommandes = reqSousReq.getResultList();
    	      for (Commande commande : listeCommandes) {
    	          System.out.println(commande.getCommandeid());
    	        }
    	          
    	      // notation avec deux points
    	      Query reqDeuxPoints = em.createQuery("SELECT c.prenom FROM Client c where c.nom = :nomChoisi ");
    	      reqDeuxPoints.setParameter("nomChoisi", "Duc");
    	      List<String> listePrenoms = reqDeuxPoints.getResultList();
    	      for (String prenom : listePrenoms) {
    	          System.out.println(prenom);
    	        }
    	      
    	       // notation avec points d'interrogation
    	      Query reqPosition = em.createQuery("SELECT c.prenom FROM Client c where c.nom = ?1 and c.titre  = ?2 ");
    	      reqPosition.setParameter(1, "Duc");
    	      reqPosition.setParameter(2, "Mr");
    	      List<String> listePrenomsPt = reqPosition.getResultList();
    	      for (String prenom : listePrenomsPt) {
    	          System.out.println(prenom);
    	        }
    	         	      
    	      // agregat + group by
    	      Query reqGroup = em.createQuery("select c.titre, count(c) from Client c group by c.titre having count(c)> 0");    	    
    	      List resultatsGroup  = reqGroup.getResultList();
    	      for (Iterator it = resultatsGroup.iterator(); it.hasNext();) {
    	    	  Object[] obj = (Object[]) it.next();
    	            for (int i=0;i<obj.length;i++)
    	            {
    	                System.out.println(obj[i]);
    	            }    	    	
    	        }
    	      	      
    	      // Requête nommée
    	      Query q = em.createNamedQuery("Client.findByTitre");    
    	      q.setParameter("leTitre", "Mlle");
    	      List<Client> resultatsNamedQuery  = q.getResultList();
    	      for (Client p : resultatsNamedQuery) {
    	          System.out.println(p.getPrenom());
    	        }
    	        
    	      // Requête native
    	      Query reqNative = em.createNativeQuery(INSERE_CATALOGUE);
    	      reqNative.setParameter(1,"Pour les litteraires");
    	      reqNative.setParameter(2, "Littérature");    	        	      
    	      EntityTransaction tx = em.getTransaction();
              tx.begin();
    	      reqNative.executeUpdate();
              tx.commit();
              
           // Requête polymorphique
              Query reqPoly = em.createQuery("select u.prenom from Utilisateur u");
              List<String>  resultatsPoly = reqPoly.getResultList();
              for (String prenom : resultatsPoly) {
    	          System.out.println(prenom);  	
    	        }
                  	      
        	logger.info("fin de testPersistence()");
        } catch (Exception ex) {
           
            ex.printStackTrace();
            fail("Exception pendant le test testPersistence");
        }
    }
}
