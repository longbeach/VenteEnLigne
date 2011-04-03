package com.eni.dvtejb31.ejb.sessionbeans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.ScheduleExpression;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.TimerService;
import javax.inject.Inject;

import com.eni.dvtejb31.ejb.entitybeans.Contact;

@Stateless
public class SessionBeanSchedulerExpr {
	
	List<Contact> listeContacts = new ArrayList<Contact>() ;
	
	// Injection CDI
	@Inject 
	private ContactServiceBean contactService;
	
	 @Resource 
	 TimerService timerService; 

	 @PostConstruct 
    public void init() {   
		ScheduleExpression expr = new ScheduleExpression().dayOfWeek("1,5").hour(10).minute(0);
		// PAs encore dispo dans Jboss 6.0 M2. Sera dispo dans M3.
		//Timer timer = timerService.createCalendarTimer(expr);
    }  
	
	 @Timeout 
	 public void execute(){ 
			listeContacts = contactService.findContacts();
		 } 
}
