package com.eni.dvtejb.web;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.TransactionManagement;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.eni.dvtejb.client.ClientDAOTest;
import com.eni.dvtejb.metier.entities.Adresse;
import com.eni.dvtejb.metier.entities.Article;
import com.eni.dvtejb.metier.entities.Client;
import com.eni.dvtejb.metier.entities.Produit;
import com.eni.dvtejb.metier.entities.Stock;

// Utilise persistence.xml avec JTA 
@TransactionManagement(javax.ejb.TransactionManagementType.BEAN)
public class TestTransactionBMT extends javax.servlet.http.HttpServlet {  
	
    static final long serialVersionUID = 1L;  
    
    private static Logger logger = Logger.getLogger(TestTransactionBMT.class.getName());
   
   // @EJB (mappedName="VenteEnLigne/PanierBean/remote")
   // private PanierBeanRemote panier;
    
    // An EJB3 container injects the persistence unit and persistence context.
    //@PersistenceUnit(unitName="VenteEnLigneClientTest") private EntityManagerFactory emFactory;
    //@PersistenceContext(unitName="VenteEnLigneClientTest") private EntityManager em;
   
    @Resource
    private javax.transaction.UserTransaction userTx;
    
    private EntityManagerFactory emFactory;
    private EntityManager em;
        
    /*
    @Resource (mappedName="java:/OracleDS")
    private  DataSource OracleDS;

    public void insertClient(Client client) {
        // Connexion à la base
        try {
			Connection connexion = OracleDS.getConnection();
			 System.out.println(" %%%%%%% Connexion obtenue ");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}      
    } 
*/
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {  
        try { 
            //InitialContext initialContext = getInitialContext(); 
        	//InitialContext initialContext = new InitialContext(); 
            //PanierBeanRemote panier = (PanierBeanRemote) initialContext.lookup("VenteEnLigne/PanierBean/remote"); 
                   	
            emFactory = Persistence.createEntityManagerFactory("VenteEnLigneModuleEJB");
            em = emFactory.createEntityManager();
                      
            userTx.begin();
            em.joinTransaction();

            logger.info(" ----------- Debut -----------");
                     
            //Ajout d'un client
            
            Adresse adresse = new Adresse();
            BigDecimal codePostal = new BigDecimal("75110");
            adresse.setCodepostal(codePostal);
            adresse.setDepartement("Paris");
            BigDecimal numero = new BigDecimal("45");
            adresse.setNumero(numero);
            adresse.setPays("France");
            adresse.setRue("Lafayette");
            adresse.setVille("Paris");                        
            logger.info("Sauvegarde en base de l'adresse");
            em.persist(adresse);
                        
            Client client = new Client();
            client.setEmail("client1@super.com");
            BigDecimal fax = new BigDecimal("1115333");
            client.setFax(fax);
            client.setLogin("azerty");
            client.setPassword("qwerty");
            client.setNom("Dabet");
            client.setPrenom("Jean");
            BigDecimal telephone = new BigDecimal("4567899");
            client.setTelephone(telephone);
            client.setTitre("Mr");
            client.setAdresseFk(adresse);
            logger.info("Sauvegarde en base du client");
            em.persist(client);
            
            Stock stock = new Stock();
            stock.setStockid(22);
            BigDecimal quantite = new BigDecimal("99");
            stock.setQuantite(quantite);
            logger.info("Sauvegarde en base du stock");
            em.persist(stock);
            
            // Recherche du produit 
            logger.info("Recherche du produit");
            
            Produit produit = em.find(Produit.class, 5L);
            
            Article article = new Article();
            article.setArticleid(22);
            article.setNom("Papier Peint");
            article.setPrix(23.23);
            article.setProduitFk(produit);
            article.setStockFK(stock);
            logger.info("Sauvegarde en base de l'article");
            em.persist(article);          
                             
            userTx.commit();        
            logger.info(" ----------- Commit effectué -----------");       
            
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
    }  
    
     // Configuration du JNDI
    /*
    private static InitialContext getInitialContext() throws NamingException{
    Properties properties = new Properties();
    properties.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY,    "org.jnp.interfaces.NamingContextFactory");
    properties.put(javax.naming.Context.PROVIDER_URL,    "jnp://localhost:1099");
    properties.put(javax.naming.Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
    return new javax.naming.InitialContext(properties);
      }
      */
}  