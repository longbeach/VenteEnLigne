package com.eni.dvtejb.metier.dtos;

import java.util.Date;

public class CommandeDTO {

	private long commandeId;
	private String nomArticle;
	private Date dateCommande;

	/* DTO pour la requête suivante :   
	 * "select c.CommandeID,  a.nom, c.DATECOMMANDE "+
				"from Commande c, Utilisateur u, Lignecommande_tj tj, lignecommande lc, article a "+
				"where c.UTILISATEUR_FK = u.UTILISATEURID "+
				"and c.COMMANDEID = tj.COMMANDE_FK "+
				"and lc.LIGNECOMMANDEID = tj.LIGNECOMMANDE_FK "+
				"and lc.ARTICLE_FK = a.ARTICLEID "+
				"and u.UTILISATEURID = ?1 "
	 */
	public CommandeDTO() {		
	}
		
	public CommandeDTO(long commandeId, String nomArticle, Date dateCommande) {
		super();
		this.commandeId = commandeId;
		this.nomArticle = nomArticle;
		this.dateCommande = dateCommande;
	}
	
	public long getCommandeId() {
		return commandeId;
	}

	public void setCommandeId(long commandeId) {
		this.commandeId = commandeId;
	}

	public String getNomArticle() {
		return nomArticle;
	}

	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}

	public Date getDateCommande() {
		return dateCommande;
	}

	public void setDateCommande(Date dateCommande) {
		this.dateCommande = dateCommande;
	}
}
