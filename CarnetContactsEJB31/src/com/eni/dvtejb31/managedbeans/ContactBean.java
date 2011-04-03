package com.eni.dvtejb31.managedbeans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

import org.apache.log4j.Logger;

import com.eni.dvtejb31.ejb.entitybeans.Contact;
import com.eni.dvtejb31.ejb.sessionbeans.ContactServiceBean;

@ManagedBean
@SessionScoped
public class ContactBean {
	
	private static final Logger log = Logger.getLogger(ContactBean.class);	
	private final static String LISTE_CONTACTS = "listeContacts.xhtml";
	private final static String MODIFIER_CONTACT = "modifierContact.xhtml";
		
	@EJB
	private ContactServiceBean contactService;
	
	private DataModel<Contact> contacts;
	private Contact contactCourant;
	
	@PostConstruct
	public void init() {	
		rafraichir();		
	}
	
	public DataModel<Contact> getContacts() {
		return contacts;
	}
	
	public void rafraichir() {
		contacts = new ListDataModel<Contact>();    
		contacts.setWrappedData(contactService.findContacts());
	}
	
	public String ajouter() {
		contactCourant = contactService.ajouter();
		return MODIFIER_CONTACT;
	}
	
	public String supprimer() {
		Contact contact = contacts.getRowData();
		contactService.supprimer(contact);
		rafraichir();
		return LISTE_CONTACTS;
	}
	
	public String modifier() {
		contactCourant = contacts.getRowData();
		rafraichir();
		return MODIFIER_CONTACT;
	}
	
	public String sauver() {
		contactService.updater(contactCourant);
		rafraichir();
		return LISTE_CONTACTS;
	}

	public String annuler() {
		rafraichir();
		return LISTE_CONTACTS;
	}
	
	public Contact getContactCourant() {
		return contactCourant;
	}

	public void setContactCourant(Contact contactCourant) {
		this.contactCourant = contactCourant;
	}
}
