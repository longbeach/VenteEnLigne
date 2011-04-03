package com.eni.dvtejb.client;

import java.io.FileInputStream;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;

import org.jboss.logging.Logger;

public class MailConfirmationProducteur {
	
 private static final Logger log = Logger.getLogger(MailConfirmationProducteur.class);

 public static void main(String[] args) throws Exception {
	 
	  Properties proprietes = new Properties();  
	  proprietes.load(new FileInputStream("jndi.properties")); 
      InitialContext ctx = new InitialContext(proprietes);
 
      // 1: recherche d'une connection factory
	  ConnectionFactory factory = (ConnectionFactory) ctx.lookup("ConnectionFactory");
	  
	  // 2: création d'une connection JMS
	  Connection conn = factory.createConnection();

	  // 3: création d'une session
	  Session session = conn.createSession(false,Session.AUTO_ACKNOWLEDGE);

      // 4: Recherche d'une destination
	  Topic topic = (Topic) ctx.lookup("topic/MailConfirmationMdbTopic");

	  // 5: création d'un producteur de message
	  MessageProducer producteur = session.createProducer(topic);

	  // 6: publication d'un message
	  TextMessage msg = session.createTextMessage();
	  msg.setText("Mail de confirmation pour le client.");
	  producteur.send(msg);

	  producteur.close(); 
	  log.info("Message envoyé.");
 }
}
