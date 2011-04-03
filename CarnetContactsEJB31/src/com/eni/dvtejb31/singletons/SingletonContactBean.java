package com.eni.dvtejb31.singletons;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import org.apache.log4j.Logger;

import com.eni.dvtejb31.ejb.entitybeans.Contact;

@Singleton
public class SingletonContactBean {
	
	private static final Logger log = Logger.getLogger(SingletonContactBean.class);	

	@PersistenceContext(unitName = "CarnetContactsJavaEE", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;
	
	private Contact contactPrincipal;
	
	@PostConstruct
	private void init() {
		contactPrincipal = entityManager.find(Contact.class, 1);
	   log.info("Le nom du contact est : " + contactPrincipal.getNom());	   
	}
	
	@PreDestroy
	private void destroy() {
	    entityManager.merge(contactPrincipal);
	}
	
	public void setContactPrincipal(Contact contact) {
	   this.contactPrincipal = contact;
	}
	
	public Contact getContactPrincipal() {
	  return contactPrincipal;
	}
}
