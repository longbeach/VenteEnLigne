package com.eni.dvtejb.metier.sessions;

import java.math.BigDecimal;
import java.util.List;

import com.eni.dvtejb.metier.dtos.MagasinDTO;
import com.eni.dvtejb.metier.entities.Article;
import com.eni.dvtejb.metier.entities.Catalogue;
import com.eni.dvtejb.metier.entities.Produit;

public interface RechercheRemote {
	
	public List<Article> rechercheArticles(String nomArticle, BigDecimal prixMinimum, BigDecimal prixMaximum);
	public List rechercheNomArticles();

	public List<Produit> rechercheListeProduits();
	public List<Catalogue> rechercheListeCatalogues();
	public List<MagasinDTO> rechercheArticleParProduit(String nomProduit);
	public List<MagasinDTO> rechercheArticleParCatalogue(long idCatalogue);
	
	public List<Article> rechercheArticleParNom(String nomArticle);
}
