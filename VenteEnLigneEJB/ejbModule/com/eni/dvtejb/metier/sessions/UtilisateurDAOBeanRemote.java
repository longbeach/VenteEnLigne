package com.eni.dvtejb.metier.sessions;

import java.util.List;

import javax.ejb.Remote;

import com.eni.dvtejb.metier.entities.Utilisateur;

@Remote
public interface UtilisateurDAOBeanRemote {

	 public List<Utilisateur> rechercherTous();
	 public void sauver(Utilisateur person); 
	 public void supprimer(long id);
	 public Utilisateur rechercher(long id);

}
