package com.eni.dvtejb.metier.sessions;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import com.eni.dvtejb.metier.entities.Utilisateur;

//@SecurityDomain("venteEnLigne_domaine")
// @SecurityDomain("venteEnLigne_domaine_DB") // A remettre pour test
@RolesAllowed({"A", "C"})
@Stateless
@Remote(GererUtilisateur.class)
public class GererUtilisateurBean implements GererUtilisateur {

	@RolesAllowed({"A"})
	public void ajouter(Utilisateur utilisateur) {
		
		// TODO : code pour créer / persister un nouvel utilisateur
		System.out.println("Ajout d'un utilisateur");

	}

	@RolesAllowed ("A")
	public void supprimer(Utilisateur utilisateur) {
	
		// TODO : code pour supprimer un  utilisateur
		System.out.println("Suppression d'un utilisateur");
	}
	
	@PermitAll
	public void modifier(Utilisateur utilisateur) {
		
		// TODO : code pour supprimer un  utilisateur
		System.out.println("Modification d'un utilisateur");
	}
	
	
}
