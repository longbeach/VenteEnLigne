package com.eni.dvtejb.metier.dtos;

import java.math.BigDecimal;

public class MagasinDTO {

	private String nomCatalogue;
	private String nomProduit;
	private String nomArticle;
	private long id;
	private double prix;
	private BigDecimal quantite;
	
	/* DTO pour la requête suivante : 
	 * select c.nom, p.nom, a.nom, a.articleid, a.prix, s.quantite  from Catalogue c, Produit p, Article a, Stock s" +
				" where c.catalogueid = p.catalogueFk" +
				" and p.produitid = a.produitFk" +
				" and a.stockFK = s.stockid"
	 */
	public MagasinDTO() {		
	}
		
	public MagasinDTO(String nomCatalogue, String nomProduit,
			String nomArticle, long id, double prix, BigDecimal quantite) {
		super();
		this.nomCatalogue = nomCatalogue;
		this.nomProduit = nomProduit;
		this.nomArticle = nomArticle;
		this.id = id;
		this.prix = prix;
		this.quantite = quantite;
	}

	public String getNomCatalogue() {
		return nomCatalogue;
	}
	public void setNomCatalogue(String nomCatalogue) {
		this.nomCatalogue = nomCatalogue;
	}
	public String getNomProduit() {
		return nomProduit;
	}
	public void setNomProduit(String nomProduit) {
		this.nomProduit = nomProduit;
	}
	public String getNomArticle() {
		return nomArticle;
	}
	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}	
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	public BigDecimal getQuantite() {
		return quantite;
	}
	public void setQuantite(BigDecimal quantite) {
		this.quantite = quantite;
	}
}
