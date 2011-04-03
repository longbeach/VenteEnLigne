package com.eni.dvtejb31.ejb.sessionbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Schedule;
import javax.ejb.Schedules;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.eni.dvtejb31.ejb.entitybeans.Contact;

@Stateless
public class SessionBeanScheduler {
	
	List<Contact> listeContacts = new ArrayList<Contact>() ;
	
	// Injection CDI
	@Inject 
	private ContactServiceBean contactService;

	@Schedule(dayOfWeek= "Mon-Fri", hour= "10", minute = "00") 
    public void execute() {   
		listeContacts = contactService.findContacts();
    }  
	
	@Schedules(
			{@Schedule(dayOfWeek= "Mon-Fri", hour= "10", minute = "00"),
			@Schedule(hour="20") 
			})
    public void executeBis() {   
		listeContacts = contactService.findContacts();
    } 
}
