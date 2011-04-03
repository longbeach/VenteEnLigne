package com.eni.dvtejb.metier.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name="SeqAdresse", sequenceName="adresse_seq")
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public class Adresse implements Serializable {	
	
	@Id
	@ GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SeqAdresse")
	private long adresseid;

	private BigDecimal codepostal;
	private String departement;
	private BigDecimal numero;
	private String pays;
	private String rue;
	private String ville;

	@OneToMany(mappedBy="adresseFk")
	private Set<Commande> commandeCollection;

	@OneToMany(mappedBy="adresseFk")
	private Set<Utilisateur> clientCollection;

	private static final long serialVersionUID = 1L;

	public Adresse() {
		super();
	}

	public long getAdresseid() {
		return this.adresseid;
	}

	public void setAdresseid(long adresseid) {
		this.adresseid = adresseid;
	}

	public BigDecimal getCodepostal() {
		return this.codepostal;
	}

	public void setCodepostal(BigDecimal codepostal) {
		this.codepostal = codepostal;
	}

	public String getDepartement() {
		return this.departement;
	}

	public void setDepartement(String departement) {
		this.departement = departement;
	}

	public BigDecimal getNumero() {
		return this.numero;
	}

	public void setNumero(BigDecimal numero) {
		this.numero = numero;
	}

	public String getPays() {
		return this.pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public String getRue() {
		return this.rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getVille() {
		return this.ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public Set<Commande> getCommandeCollection() {
		return this.commandeCollection;
	}

	public void setCommandeCollection(Set<Commande> commandeCollection) {
		this.commandeCollection = commandeCollection;
	}

	public Set<Utilisateur> getClientCollection() {
		return this.clientCollection;
	}

	public void setClientCollection(Set<Utilisateur> clientCollection) {
		this.clientCollection = clientCollection;
	}

}
