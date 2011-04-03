package com.eni.dvtejb.clientJSF2.services;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.logging.Logger;

public class EnvoiMail {
	
	private static final Logger log = Logger.getLogger(EnvoiMail.class);
	
	public EnvoiMail() {
	}
	
	public void envoiMailMessage(String sujet, String expediteur, String body, boolean mailCC) throws MessagingException, NamingException, UnsupportedEncodingException {
		
	Properties props = new Properties();
	InitialContext ictx = new InitialContext(props);
	javax.mail.Session mailSession = (javax.mail.Session) ictx.lookup("java:/Mail");
	String email = "eniejb3@gmail.com";    
    String corps = body + " \n " + "Email du client : " + expediteur;
		
	// Le destinataire du mail est paramétré dans deploy/mail-service.xml
	MimeMessage message = new MimeMessage(mailSession);
	
	if (mailCC) {
		message.addRecipients(javax.mail.Message.RecipientType.CC, 
				javax.mail.internet.InternetAddress.parse(expediteur, false));
	}
	message.setSubject(sujet);
	message.addRecipients(javax.mail.Message.RecipientType.TO, 
			javax.mail.internet.InternetAddress.parse(email, false));
	message.setText(corps);
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
}
