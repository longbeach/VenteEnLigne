package com.eni.dvtejb.client;

import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import junit.framework.TestCase;
 
import com.eni.dvtejb.metier.entities.Adresse;
import com.eni.dvtejb.metier.entities.Client;
import com.eni.dvtejb.metier.entities.Commande;

/*
 * Le serveur n'a pas besoin d'être démarré pour exécuter ce test.
 * JPA peut également fonctionner hors container.
 * Le fichier persistence.xml doit être paramétré correctement.
 */
public class PersistenceHorsContainerTest extends TestCase {
	
	private static Logger logger = Logger.getLogger(PersistenceHorsContainerTest.class.getName());
    private EntityManagerFactory emFactory;
    private EntityManager em;
   
    public PersistenceHorsContainerTest(String testName) {
        super(testName);
    }

    @Override
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

    @Override
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

    public void testPersistence() {
        try {
        	logger.info("debut de testPersistence()");
           em.getTransaction().begin();
            logger.info("em.getTransaction().begin()");
                        
            Client client = new Client();
            client.setNom("renard");
            client.setPrenom("lulu");
            BigDecimal fax = new BigDecimal("1115333");
            client.setFax(fax);
            client.setLogin("renard");
            client.setPassword("lulu");
            BigDecimal telephone = new BigDecimal("1115333");
            client.setTelephone(telephone);
            client.setTitre("Mr");
            client.setEmail("renard@lulu.com");
            logger.info("avant persist");
            em.persist(client);
            logger.info("apres persist");
            
            assertTrue(em.contains(client));

            Commande commande = new Commande();
            commande.setTypeCartecredit("Visa");
            
            Date aujourdhui = new Date();
            long t = aujourdhui.getTime();
            java.sql.Date aujourdhuiSQL =  new java.sql.Date(t);
            commande.setDatecommande(aujourdhuiSQL);
            
            // Clé étrangère
            commande.setUtilisateurFk(client);   
            
            java.sql.Date expirationDate = java.sql.Date.valueOf( "2010-01-31" );
    		commande.setDateExpirationCartecredit( expirationDate);            
            commande.setNumeroCartecredit("4123654787651234");           

            em.persist(commande);
            assertTrue(em.contains(commande));
            
            Adresse adresse = new Adresse();
            BigDecimal codepostal = new BigDecimal("75000");
            adresse.setCodepostal(codepostal);
            adresse.setDepartement("Paris");
            adresse.setVille("Paris");
            adresse.setPays("France");
            adresse.setRue("Vaugirard");
            BigDecimal numero = new BigDecimal("230");
            adresse.setNumero(numero);

            em.persist(adresse);
            em.remove(adresse);
            assertFalse(em.contains(adresse));

            em.getTransaction().commit();
        	logger.info("fin de testPersistence()");
        } catch (Exception ex) {
            em.getTransaction().rollback();
            ex.printStackTrace();
            fail("Exception pendant le test testPersistence");
        }
    }
}
