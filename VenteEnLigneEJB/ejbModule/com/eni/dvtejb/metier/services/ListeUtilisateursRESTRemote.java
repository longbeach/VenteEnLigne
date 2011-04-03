package com.eni.dvtejb.metier.services;

import java.math.BigDecimal;

import javax.ejb.Remote;
import javax.ws.rs.core.Response;

@Remote
public interface ListeUtilisateursRESTRemote {
	
	  ListeNomsUtilisateurs ListerUtilisateurs();	 
	  String getPrenomUtilisateur(String nom);
	  Response changeNumeroRue(Long adresseId,  BigDecimal numeroRue);
	  String recupererPrixArticle(String nomArticle);
	  Response updaterPwd( Long idUtilisateur, String pwd);
}
