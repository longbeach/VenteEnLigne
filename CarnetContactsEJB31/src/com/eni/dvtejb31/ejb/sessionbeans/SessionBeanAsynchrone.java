package com.eni.dvtejb31.ejb.sessionbeans;

import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import com.eni.dvtejb31.ejb.entitybeans.Contact;

@LocalBean
@Stateless
public class SessionBeanAsynchrone {

	@PersistenceContext(unitName = "CarnetContactsJavaEE", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;
	 
	@Asynchronous
	public void ajouterContact(Contact contact) {
		 entityManager.persist(contact);		
	}
	 
	@Asynchronous
	public Future<Long> creerContact(){
	  Contact c = new Contact();
	  c.setNom("nomSynch");
	  c.setPrenom("prenomSynch");
	  entityManager.persist(c);
	  entityManager.flush();
	  entityManager.refresh(c);
	  return new AsyncResult<Long>(c.getContactid());
    }
}
