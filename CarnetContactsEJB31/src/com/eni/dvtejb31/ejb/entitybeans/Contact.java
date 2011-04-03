package com.eni.dvtejb31.ejb.entitybeans;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@SequenceGenerator(name="SeqContact", sequenceName="contact_seq")
@Entity
public class Contact implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Contact() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SeqContact")
	private long contactid;
	
	private String nom;
	private String prenom;
	private String titre;
	private String email;
	private BigDecimal telephone;

	public long getContactid() {
		return contactid;
	}
	public void setContactid(long contactid) {
		this.contactid = contactid;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public BigDecimal getTelephone() {
		return telephone;
	}
	public void setTelephone(BigDecimal telephone) {
		this.telephone = telephone;
	}	
}
