package com.eni.dvtejb.metier.entities;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQuery(name="Catalogue.findByCatalogue", query=" SELECT DISTINCT  c FROM Catalogue c ")
public class Catalogue implements Serializable {
	@Id
	private long catalogueid;

	private String description;
	private String nom;

	@OneToMany(mappedBy="catalogueFk")
	private Set<Produit> produitCollection;

	private static final long serialVersionUID = 1L;

	public Catalogue() {
		super();
	}

	public long getCatalogueid() {
		return this.catalogueid;
	}

	public void setCatalogueid(long catalogueid) {
		this.catalogueid = catalogueid;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Set<Produit> getProduitCollection() {
		return this.produitCollection;
	}

	public void setProduitCollection(Set<Produit> produitCollection) {
		this.produitCollection = produitCollection;
	}

}
