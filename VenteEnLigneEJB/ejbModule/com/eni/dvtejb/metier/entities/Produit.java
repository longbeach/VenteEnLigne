package com.eni.dvtejb.metier.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQuery(name="Produit.findByProduit", query=" SELECT DISTINCT  p FROM Produit p ")
public class Produit implements Serializable {
	@Id
	private long produitid;

	private String description;
	private String nom;

	@ManyToOne
	@JoinColumn(name="CATALOGUE_FK")
	private Catalogue catalogueFk;

	@OneToMany(mappedBy="produitFk")
	private Set<Article> articleCollection;

	private static final long serialVersionUID = 1L;

	public Produit() {
		super();
	}

	public long getProduitid() {
		return this.produitid;
	}

	public void setProduitid(long produitid) {
		this.produitid = produitid;
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

	public Catalogue getCatalogueFk() {
		return this.catalogueFk;
	}

	public void setCatalogueFk(Catalogue catalogueFk) {
		this.catalogueFk = catalogueFk;
	}

	public Set<Article> getArticleCollection() {
		return this.articleCollection;
	}

	public void setArticleCollection(Set<Article> articleCollection) {
		this.articleCollection = articleCollection;
	}

}
