package com.eni.dvtejb.metier.sessions;

import java.util.List;

import com.eni.dvtejb.metier.entities.Adresse;
import com.eni.dvtejb.metier.entities.Client;
import com.eni.dvtejb.metier.entities.Utilisateur;

public interface FacadeRemote {
		
	public Utilisateur findUtilisateurByLoginPwd(String login, String pwd);
	public Utilisateur findUtilisateurById(long utilisateurId);
	public Adresse findAdresseById(long adresseId);		
	public boolean updateClient(Client client, Adresse adresse);	
	public List findMagasin();
	public List findBoutique(); // Pour client JSF 2. Methode inutilisée.
	public String findMdpByLoginEmail(String login, String email); // Pour client JSF 2
	public List findBoutiqueIntervalle(int max, int premier); // Pour client JSF 2
	public int getNombreTotalArticles(); // Pour client JSF 2 
	public String testPourFlex();
}
