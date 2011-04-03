package com.eni.dvtejb.metier.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
//@NamedQuery(name="Client.findByPrenom", 
//		query="select c from Client c where c.prenom = :lePrenom")
@SequenceGenerator(name="SeqUtilisateur", sequenceName="utilisateur_seq")
@Table(name="UTILISATEUR", schema="HR")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE_UTIL", discriminatorType=DiscriminatorType.STRING, length=1)
public abstract class Utilisateur implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SeqUtilisateur")
	private long utilisateurid;

	private String email;
	private BigDecimal fax;
	private String login;
	private String nom;
	private String password;
	private String prenom;
	private BigDecimal telephone;
	private String titre;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="ADRESSE_FK")
	private Adresse adresseFk;

	@OneToMany(mappedBy="utilisateurFk")
	private Set<Commande> commandeCollection;

	private static final long serialVersionUID = 1L;

	public Utilisateur() {
		super();
	}

	public long getUtilisateurid() {
		return this.utilisateurid;
	}

	public void setUtilisateurid(long clientid) {
		this.utilisateurid = clientid;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getFax() {
		return this.fax;
	}

	public void setFax(BigDecimal fax) {
		this.fax = fax;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPrenom() {
		return this.prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public BigDecimal getTelephone() {
		return this.telephone;
	}

	public void setTelephone(BigDecimal telephone) {
		this.telephone = telephone;
	}

	public String getTitre() {
		return this.titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public Adresse getAdresseFk() {
		return this.adresseFk;
	}

	public void setAdresseFk(Adresse adresseFk) {
		this.adresseFk = adresseFk;
	}

	public Set<Commande> getCommandeCollection() {
		return this.commandeCollection;
	}

	public void setCommandeCollection(Set<Commande> commandeCollection) {
		this.commandeCollection = commandeCollection;
	}
	
}
