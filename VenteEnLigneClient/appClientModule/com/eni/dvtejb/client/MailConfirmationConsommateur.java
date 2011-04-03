package com.eni.dvtejb.client;

import java.io.FileInputStream;
import java.util.Properties;

import javax.annotation.PreDestroy;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;

public class MailConfirmationConsommateur implements MessageListener {

	 public static void main(String[] args) throws Exception {
		 new MailConfirmationConsommateur();
	 }

	 public MailConfirmationConsommateur() throws Exception {
		 
		  Properties proprietes = new Properties();  
		  proprietes.load(new FileInputStream("jndi.properties")); 
	      InitialContext ctx = new InitialContext(proprietes);
		
	      // 1:  recherche d'une connection factory
		  ConnectionFactory factory = (ConnectionFactory) ctx.lookup("ConnectionFactory");

		  // 2:  création d'une connection JMS
		  Connection conn = factory.createConnection();
		  
		  // 3: création d'une session
		  Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		    
		  // 4. Recherche d'une destination
		  Topic topic = (Topic) ctx.lookup("topic/MailConfirmationMdbTopic");     
		    
		  // 5: création d'un consommateur de message
		  MessageConsumer consommateur = session.createConsumer(topic);  
		  consommateur.setMessageListener(this);

		  System.out.println("Client JMS MailConfirmationConsommateur à l'écoute de messages.");
		  conn.start();   // 
	 }

	 public void onMessage(Message msg) {
		  if (msg instanceof TextMessage) {
		   TextMessage tm = (TextMessage) msg;  
		   
		   // L'envoi d'un mail de confirmation au client est ici simulé 
		   // par l'affichage d'un message au niveau des logs.
		   try {
		    String mail = tm.getText();
		    System.out.println("Le client JMS MailConfirmationConsommateur a reçu le message : " + mail);
		   } catch (JMSException e) {
		    e.printStackTrace();
		   }
		  }
	 }

	 @PreDestroy 
	 public void remove() {
		 System.out.println("Suppression du client JMS MailConfirmationConsommateur.");
	 }
}