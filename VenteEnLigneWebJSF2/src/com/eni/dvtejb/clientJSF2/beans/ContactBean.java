package com.eni.dvtejb.clientJSF2.beans;

import java.io.UnsupportedEncodingException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.mail.MessagingException;
import javax.naming.NamingException;
import javax.validation.constraints.Pattern;

import org.apache.log4j.Logger;

import com.eni.dvtejb.clientJSF2.services.EnvoiMail;

@ManagedBean
@SessionScoped
public class ContactBean {
	
	private static final Logger log = Logger.getLogger(ContactBean.class);
	
	private String message;
	private boolean mailEnvoye;
	private boolean mailCC;
	
	public boolean isMailCC() {
		return mailCC;
	}

	public void setMailCC(boolean mailCC) {
		this.mailCC = mailCC;
	}

	public String mailClient; // L'adresse email du client

	public String getMailClient() {
		return mailClient;
	}

	public void setMailClient(String mailClient) {
		this.mailClient = mailClient;
	}

	public ContactBean() {		
		mailEnvoye = false;
	}

	public boolean isMailEnvoye() {
		return mailEnvoye;
	}

	public void setMailEnvoye(boolean mailEnvoye) {
		this.mailEnvoye = mailEnvoye;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void envoiMail() {
		EnvoiMail envoyerMail = new EnvoiMail();
		try {
			envoyerMail.envoiMailMessage("Questions client", mailClient, message, mailCC);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		this.mailEnvoye = true;
	}

}
