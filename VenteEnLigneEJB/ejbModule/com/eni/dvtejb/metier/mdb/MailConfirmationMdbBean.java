package com.eni.dvtejb.metier.mdb;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.PreDestroy;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

import com.eni.dvtejb.metier.entities.Adresse;
import com.eni.dvtejb.metier.entities.Article;
import com.eni.dvtejb.metier.entities.Client;
import com.eni.dvtejb.metier.entities.Commande;
import com.eni.dvtejb.metier.entities.Lignecommande;

@MessageDriven(mappedName = "topic/MailConfirmationMdbTopic", 
activationConfig = {
 //@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
 //@ActivationConfigProperty(propertyName="subscriptionDurability", propertyValue="Durable"),
 //@ActivationConfigProperty(propertyName="subscriptionName", propertyValue="topicmdb"),
 //@ActivationConfigProperty(propertyName="clientId", propertyValue="mdbtopic-test"),
        
 @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
 @ActivationConfigProperty(propertyName = "destination", propertyValue = "topic/MailConfirmationMdbTopic")  
})

public class MailConfirmationMdbBean implements MessageListener {

	private static final Logger log = Logger.getLogger(MailConfirmationMdbBean.class);
	
	 public MailConfirmationMdbBean(){
		 log.info("Initialisation de l'envoi du mail depuis MailConfirmationMdbBean");  
	 }
	
	 public void onMessage(Message message) { 
		 
		// Pour la classe de test MailConfirmationProducteur
	  if (message instanceof TextMessage) {
		   TextMessage mail = (TextMessage) message;
		   // L'envoi d'un mail de confirmation au client est ici simulé par l'affichage d'un message au niveau des logs.
		   try {
		    String leMail = mail.getText();
		    log.info(" Envoi du mail : " + leMail);
		    log.info(" --------------------------------------------------- ");
		    //sendMsg("eniejb3@gmail.com", "Confirmation de commande.", leMail);
		    log.info(" --------------------------------------------------- ");
		    log.info(" Mail envoyé.");
		   }
		
		   catch (JMSException e) {
		    e.printStackTrace();
		   } 
	  } else if  (message instanceof ObjectMessage) {
		  ObjectMessage lemessage = (ObjectMessage) message;
		  				  
		  try {
			   Commande commande = (Commande)lemessage.getObject();
			    Client client = commande.getUtilisateurFk();
			    Adresse adresse = client.getAdresseFk();
			    String contenuMail = "Bonjour " + client.getNom() + " " + client.getPrenom() + ". \n" 
			    + "Votre numéro de commande est : " + commande.getCommandeid()
			    + " \n" + "Vous avez commandé les articles suivants : " + " \n" ;
			    
			    String lesArticles = "";					    
			    Set <Lignecommande> listeArticles = commande.getLignecommandeCollection();
			    for (Lignecommande lc :  listeArticles){			    	
			    	Article article = lc.getArticleFk();
			    	lesArticles += article.getNom() + " : " + article.getPrix()  + " euros. \n" ;				
			    }
			    
			    contenuMail += lesArticles;
			    
			    String ladresse = 
			    " \n" + "Votre adresse est : "+ " \n" 
			    + adresse.getNumero()  + " rue " + adresse.getRue()  + " " + adresse.getCodepostal()  + " " + adresse.getVille();
			    
			    contenuMail += ladresse;
			    
			    contenuMail += "\n Votre commande est en cours de traitement.";	
			    
			    log.info(" Envoi du mail au client: " );
			    log.info(" --------------------------------------------------- ");
			    
			    sendMsg(client.getEmail(), "Confirmation de votre commande.", contenuMail);
			    log.info(" --------------------------------------------------- ");
			    log.info(" Mail envoyé au client.");
			   }
			    catch (MessagingException e) {
					e.printStackTrace();
				}
				catch (NamingException e) {
					e.printStackTrace();				
			   } catch (JMSException e) {
				    e.printStackTrace();
			   } catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			   }		  		  
	  }
	 }
	 
		protected void sendMsg(String email, String subject, String body) throws MessagingException, NamingException, UnsupportedEncodingException {
			
			Properties props = new Properties();
			InitialContext ictx = new InitialContext(props);
			javax.mail.Session mailSession = (javax.mail.Session) ictx.lookup("java:/Mail");
	 
			MimeMessage message = new MimeMessage(mailSession);
			
			message.setSubject(subject);
			message.setRecipients(javax.mail.Message.RecipientType.TO, javax.mail.internet.InternetAddress.parse(email, false));
			message.setText(body);
			message.saveChanges();
			
			Transport transport = mailSession.getTransport("smtp");
			try {
				transport.connect();
				transport.sendMessage(message, message.getAllRecipients());
				log.info("Message envoyé");
			}
			finally {
				transport.close();
			}
		}
	 	
	 @PreDestroy  
	 public void remove() {
		 log.info("Suppression de MailConfirmationMdbBean.");
	 }
}