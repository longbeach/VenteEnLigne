package com.eni.dvtejb.metier.sessions;

import com.eni.dvtejb.metier.entities.Utilisateur;

public interface GererUtilisateur {
	
	void ajouter(Utilisateur utilisateur);
	void supprimer(Utilisateur utilisateur);
	void modifier(Utilisateur utilisateur);
}
