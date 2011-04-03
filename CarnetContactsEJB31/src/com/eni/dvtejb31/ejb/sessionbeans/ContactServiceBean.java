package com.eni.dvtejb31.ejb.sessionbeans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.eni.dvtejb31.ejb.entitybeans.Contact;

@Stateless
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
@Remote(IContactServiceRemote.class)
@LocalBean
public class ContactServiceBean implements IContactServiceRemote {
	
	private static final Logger log = Logger.getLogger(ContactServiceBean.class);			
	List<Contact> lesContacts = new ArrayList<Contact>(10);
		
	@PersistenceContext(unitName = "CarnetContactsJavaEE", type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;
	
	public String renvoyerContacts() {		
		lesContacts = findContacts();				
        return lesContacts.get(0).getNom();
    }
		
	public Contact ajouter() {
		return new Contact();
	}
	
	public void supprimer(Contact contact) {
		// Il faut faire un merge car l'entite est détachée 
		contact = entityManager.merge(contact);  		
		entityManager.remove(contact);
	}
	
	public Contact updater(Contact contact) {
		return entityManager.merge(contact);
	}
	
	public Contact rechercher(Integer id) {
		return entityManager.find(Contact.class, id);
	}
	
	 @SuppressWarnings("unchecked")
	public List<Contact> findContacts(){
		 log.info("Methode findContacts");
			Query query = entityManager.createQuery("SELECT c FROM Contact c");
			List<Contact> listeContacts = query.getResultList();			
			return listeContacts;		
		}
}
