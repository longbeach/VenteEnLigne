package com.eni.dvtejb.metier.sessions;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.eni.dvtejb.metier.dtos.CommandeDTO;
import com.eni.dvtejb.metier.entities.Article;
import com.eni.dvtejb.metier.entities.Client;
import com.eni.dvtejb.metier.entities.Commande;
import com.eni.dvtejb.metier.entities.Utilisateur;
import com.eni.dvtejb.metier.services.ArticlePanier;

public interface PanierBeanRemote {
	
	public Commande genererCommande(Article[] listArticles, Client client, BigDecimal[] quantites);
	public void ajouterArticle(Article article, BigDecimal quantite);
	public Collection<String> getProduits();
	public Article findById(long id);
	ArrayList<ArticlePanier> getPanier();
	public double getMontantTotal();
	public void viderPanier();
	public void supprimerArticle(ArticlePanier articlePanier);
	public List afficherHistoCommandes(Utilisateur u);
	public void commander(Client client, ArrayList<ArticlePanier> articlesPanier, String numCC, String typeCC, java.sql.Date expirationDate);
	public List<CommandeDTO> afficherCommandesPrecedentes (Utilisateur u); // JSF 2

}
