package com.eni.dvtejb.metier.mdb;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.jboss.logging.Logger;

@MessageDriven(mappedName = "topic/MailConfirmationMdbTopic", 
		activationConfig = {
		 @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"),
		 @ActivationConfigProperty(propertyName = "destination", propertyValue = "topic/MailConfirmationMdbTopic")  
		})
public class TraitementCarteCreditMdbTimerBean implements MessageListener {
	
	private static final Logger log = Logger.getLogger(TraitementCarteCreditMdbTimerBean.class);
	
	@Resource
	private MessageDrivenContext messageDrivenCtx;
	
	private String leMail = null;

	public TraitementCarteCreditMdbTimerBean(){
		log.info(" ---------------- Initialisation de l'envoi du mail depuis TraitementCarteCreditMdbTimerBean ---------------- ");  
	 }

	 public void onMessage(Message message) { 
		 
		 TimerService timerService = messageDrivenCtx.getTimerService();
		 
		 // Création d'un timer qui expire tous les jours à 4h du matin
		 
		 // Récupérer un calendar à la date du jour
		 Calendar cal = Calendar.getInstance();
		 
		 // Fixer l'heure à 4h du matin
		 cal.set(Calendar.HOUR, 4);
	     cal.set(Calendar.MINUTE, 00);
	     cal.set(Calendar.SECOND, 0);
	     
	     // Récupérer un objet Date à partir du Calendar
	     Date date = cal.getTime();

	     long quinzesecondes = (15 * 1000);
	     long cinqsecondes = (5 * 1000);
	     
	    // Création d'un timer de test: intervalle de 15 secondes
	     Timer timer = timerService.createTimer(quinzesecondes, quinzesecondes, "TimerMDBCarteCredit");

		 
		  if (message instanceof TextMessage) {
		   TextMessage mail = (TextMessage) message;
		   try {
		    leMail = mail.getText();
		    
		   } catch (JMSException e) {
		    e.printStackTrace();
		   }
		  }
		 }
		
	 @Timeout
	 public void envoyerMail(javax.ejb.Timer timer) {
		 
		log.info("------------ Méthode envoyerMail(javax.ejb.Timer timer) : debut --------");
	    String intoTimer = (String) timer.getInfo();
	    log.info(intoTimer);
	 
	    log.info(" TraitementCarteCreditMdbTimerBean - envoi du mail : " + leMail);
	    log.info(" --------------------------------------------------- ");
	    log.info(" Mail ENVOYE ");
	    log.info(" --------------------------------------------------- ");		 
	 }
	 
	 @PreDestroy  
	 public void remove() {
		 log.info("Suppression de TraitementCarteCreditMdbTimerBean.");	  
	 }	 
}
