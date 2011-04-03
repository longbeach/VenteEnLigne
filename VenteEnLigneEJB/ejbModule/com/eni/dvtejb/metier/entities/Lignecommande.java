package com.eni.dvtejb.metier.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name="SeqLigneCommande", sequenceName="lignecommande_seq")
public class Lignecommande implements Serializable {
	
	@Id
	@ GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SeqLigneCommande")
	private long lignecommandeid;

	private BigDecimal quantite;

	@ManyToOne
	@JoinColumn(name="ARTICLE_FK")
	private Article articleFk;

	@ManyToMany(mappedBy="lignecommandeCollection")
	private Set<Commande> commandeCollection;

	private static final long serialVersionUID = 1L;

	public Lignecommande() {
		super();
	}

	public long getLignecommandeid() {
		return this.lignecommandeid;
	}

	public void setLignecommandeid(long lignecommandeid) {
		this.lignecommandeid = lignecommandeid;
	}

	public BigDecimal getQuantite() {
		return this.quantite;
	}

	public void setQuantite(BigDecimal quantite) {
		this.quantite = quantite;
	}

	public Article getArticleFk() {
		return this.articleFk;
	}

	public void setArticleFk(Article articleFk) {
		this.articleFk = articleFk;
	}

	public Set<Commande> getCommandeCollection() {
		return this.commandeCollection;
	}

	public void setCommandeCollection(Set<Commande> commandeCollection) {
		this.commandeCollection = commandeCollection;
	}

}
