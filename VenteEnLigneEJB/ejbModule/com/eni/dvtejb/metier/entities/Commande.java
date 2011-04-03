package com.eni.dvtejb.metier.entities;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name="SeqCommande", sequenceName="commande_seq")
public class Commande implements Serializable {
	
	@Id
	@ GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SeqCommande")
	private long commandeid;

	@Column(name="DATE_EXPIRATION_CARTECREDIT")
	private Date dateExpirationCartecredit;

	private Date datecommande;

	@Column(name="NUMERO_CARTECREDIT")
	private String numeroCartecredit;

	@Column(name="TYPE_CARTECREDIT")
	private String typeCartecredit;

	@ManyToOne
	@JoinColumn(name="ADRESSE_FK")
	private Adresse adresseFk;

	@ManyToOne
	@JoinColumn(name="UTILISATEUR_FK")
	private Client utilisateurFk;

	@ManyToMany
	@JoinTable(name="LIGNECOMMANDE_TJ",
		joinColumns=@JoinColumn(name="COMMANDE_FK"),
		inverseJoinColumns=@JoinColumn(name="LIGNECOMMANDE_FK"))
	private Set<Lignecommande> lignecommandeCollection;

	private static final long serialVersionUID = 1L;

	public Commande() {
		super();
	}

	public long getCommandeid() {
		return this.commandeid;
	}

	public void setCommandeid(long commandeid) {
		this.commandeid = commandeid;
	}

	public Date getDateExpirationCartecredit() {
		return this.dateExpirationCartecredit;
	}

	public void setDateExpirationCartecredit(Date dateExpirationCartecredit) {
		this.dateExpirationCartecredit = dateExpirationCartecredit;
	}

	public Date getDatecommande() {
		return this.datecommande;
	}

	public void setDatecommande(Date datecommande) {
		this.datecommande = datecommande;
	}

	public String getNumeroCartecredit() {
		return this.numeroCartecredit;
	}

	public void setNumeroCartecredit(String numeroCartecredit) {
		this.numeroCartecredit = numeroCartecredit;
	}

	public String getTypeCartecredit() {
		return this.typeCartecredit;
	}

	public void setTypeCartecredit(String typeCartecredit) {
		this.typeCartecredit = typeCartecredit;
	}

	public Adresse getAdresseFk() {
		return this.adresseFk;
	}

	public void setAdresseFk(Adresse adresseFk) {
		this.adresseFk = adresseFk;
	}

	public Client getUtilisateurFk() {
		return this.utilisateurFk;
	}

	public void setUtilisateurFk(Client utilisateurFk) {
		this.utilisateurFk = utilisateurFk;
	}

	public Set<Lignecommande> getLignecommandeCollection() {
		return this.lignecommandeCollection;
	}

	public void setLignecommandeCollection(Set<Lignecommande> lignecommandeCollection) {
		this.lignecommandeCollection = lignecommandeCollection;
	}

}
