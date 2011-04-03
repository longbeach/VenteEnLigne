package com.eni.dvtejb31.ejb.sessionbeans;

import com.eni.dvtejb31.ejb.entitybeans.Contact;

public interface IContactServiceRemote {
	
	public String renvoyerContacts();
	public Contact updater(Contact contact);
	public Contact ajouter();
	public void supprimer(Contact contact);
	public Contact rechercher(Integer id);

}
