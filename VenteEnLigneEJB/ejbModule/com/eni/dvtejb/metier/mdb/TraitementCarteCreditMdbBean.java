package com.eni.dvtejb.metier.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jboss.ejb3.annotation.ResourceAdapter;
import org.jboss.logging.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.eni.dvtejb.metier.sessions.CommandeDAO;

@MessageDriven(activationConfig =  
	// Utilisation du scheduler quartz embarqué dans JBoss
	 {@ActivationConfigProperty(propertyName = "cronTrigger", propertyValue = "0 0 4 * * ?")})  
	// Message émis toutes les 5 secondes toutes les 10 secondes :  0/10
	 //{@ActivationConfigProperty(propertyName = "cronTrigger", propertyValue = "0/10 * * * * ?")})
	 // Le Resource Adapter quartz-ra.rar est dans le répertoire server/default/deploy 
	 @ResourceAdapter("quartz-ra.rar")  
public class TraitementCarteCreditMdbBean  implements Job {
	
	private static final Logger log = Logger.getLogger(TraitementCarteCreditMdbBean.class);
	
	public TraitementCarteCreditMdbBean(){
		  log.info(" ---------------- Initialisation de l'envoi du mail depuis TraitementCarteCreditMdbBean ---------------- ");  
		 }
	
	 public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException  
	     {  
		
		try {
			 InitialContext initialContext = new InitialContext();
			 CommandeDAO commandeDAO = (CommandeDAO) initialContext.lookup("VenteEnLigne/CommandeDAOImpl/remote");   
			 commandeDAO.traitementCarteCredit();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
 
	     } 
}
