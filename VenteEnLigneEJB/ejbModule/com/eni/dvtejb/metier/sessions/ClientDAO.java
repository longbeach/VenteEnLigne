package com.eni.dvtejb.metier.sessions;

import java.util.List;
import com.eni.dvtejb.metier.entities.Client;

import javax.ejb.Remote;

@Remote
public interface ClientDAO {
	
	public List findAll();
	public List findByNom(String nom);
	public Client findById(Long id);
	public void save(Client client);
	public void merge(Client client);
	public List findByPrenom(String prenom);	
}
