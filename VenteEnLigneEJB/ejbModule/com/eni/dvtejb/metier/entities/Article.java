package com.eni.dvtejb.metier.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name="SeqArticle", sequenceName="article_seq")
public class Article implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SeqArticle")
	private long articleid;

	private String nom;
	private double prix;
	
	@Lob
	@Basic(fetch=FetchType.LAZY)
	private byte[] image;
	
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	// Ajout de ce constructeur pour RechercheAction
	public Article(long articleid, String nom, double prix) {
		this.articleid = articleid;
		this.nom = nom;
		this.prix = prix;		
	}

	@ManyToOne
	@JoinColumn(name="PRODUIT_FK")
	private Produit produitFk;

	@OneToMany(mappedBy="articleFk")
	private Set<Lignecommande> lignecommandeCollection;
	
	// Relation uni-directionelle
	@OneToOne(cascade={CascadeType.PERSIST})
	@JoinColumn(name="STOCK_FK")
	private Stock stockFK;

	private static final long serialVersionUID = 1L;

	public Article() {
		super();
	}

	public long getArticleid() {
		return this.articleid;
	}

	public void setArticleid(long articleid) {
		this.articleid = articleid;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public double getPrix() {
		return this.prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public Produit getProduitFk() {
		return this.produitFk;
	}

	public void setProduitFk(Produit produitFk) {
		this.produitFk = produitFk;
	}

	public Set<Lignecommande> getLignecommandeCollection() {
		return this.lignecommandeCollection;
	}

	public void setLignecommandeCollection(Set<Lignecommande> lignecommandeCollection) {
		this.lignecommandeCollection = lignecommandeCollection;
	}
	
	public Stock getStockFK() {
		return stockFK;
	}

	public void setStockFK(Stock stockFK) {
		this.stockFK = stockFK;
	}
}
