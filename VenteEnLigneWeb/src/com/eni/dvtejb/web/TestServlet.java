package com.eni.dvtejb.web;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.eni.dvtejb.client.PersistenceHorsContainerTest;
import com.eni.dvtejb.metier.entities.Article;
import com.eni.dvtejb.metier.entities.Client;
import com.eni.dvtejb.metier.entities.Commande;
import com.eni.dvtejb.metier.sessions.PanierBeanRemote;

public class TestServlet extends javax.servlet.http.HttpServlet {  
	
    static final long serialVersionUID = 1L;  
 	private static Logger logger = Logger.getLogger(TestServlet.class.getName());
   
    /*
    @EJB (mappedName="VenteEnLigne/PanierBean/remote")
    private PanierBeanRemote panier;  
    private EntityManagerFactory emFactory;
    private EntityManager em;
       */
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
    	
        try { 
            // InitialContext initialContext = getInitialContext(); 
        	InitialContext initialContext = new InitialContext(); 
            PanierBeanRemote panier = (PanierBeanRemote) initialContext.lookup("VenteEnLigne/PanierBean/remote"); 
           	
            logger.info("-->> lookup effectué");

            logger.info(" ----------- Debut -----------");
            Article article1 = new Article();
            article1.setNom("gants");
            article1.setPrix(22);
            
            Article article2 = new Article();
            article2.setNom("chaussures");
            article2.setPrix(44);
            
            Article[] listArticles = new Article[2];
            listArticles[0] = article1;
            listArticles[1] = article2;
            
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
                       
            Commande commande = panier.genererCommande(listArticles, client, new BigDecimal[]{new BigDecimal("3"), new BigDecimal("5")});
           
            System.out.println(commande.getCommandeid());
            logger.info(commande.getUtilisateurFk().getNom());
            logger.info(commande.getUtilisateurFk().getPrenom());
            logger.info(" ----------- Fin -----------");
            
        } catch (NamingException e) {  
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