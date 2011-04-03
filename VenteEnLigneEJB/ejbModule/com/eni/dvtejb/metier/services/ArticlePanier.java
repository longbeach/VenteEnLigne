package com.eni.dvtejb.metier.services;

import java.io.Serializable;
import java.math.BigDecimal;

import com.eni.dvtejb.metier.entities.Article;

public class ArticlePanier implements Serializable {

	private static final long serialVersionUID = 1L;

	public ArticlePanier() {
		super();
	}
	
    private Article article;
    private BigDecimal quantite;

    public ArticlePanier(Article article, BigDecimal quantite) {
        this.setArticle(article);
        this.setQuantite(quantite);
    }

	public void setQuantite(BigDecimal quantite) {
		this.quantite = quantite;
	}

	public BigDecimal getQuantite() {
		return quantite;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public Article getArticle() {
		return article;
	}
}
