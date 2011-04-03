package com.eni.dvtejb.clientStruts2.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;

import com.eni.dvtejb.metier.entities.Article;
import com.eni.dvtejb.metier.services.ServiceLocator;
import com.eni.dvtejb.metier.sessions.RechercheRemote;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class RechercheAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(RechercheAction.class);

	/**
	 * Informations pour la recherche
	 */
	private String nomArticle = "";
	private BigDecimal prixMin;
	private BigDecimal prixMax;

	private List<Article> articles = new ArrayList<Article>();

	// Pour AJAX autocomplete
	private List nomsArticles;

	public List getNomsArticles() {
		return nomsArticles;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public String execute() throws Exception {
		Map session = ActionContext.getContext().getSession();
		return SUCCESS;
	}

	public String getNomArticle() {
		return nomArticle;
	}

	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}

	public BigDecimal getPrixMin() {
		return prixMin;
	}

	public void setPrixMin(BigDecimal prixMin) {
		this.prixMin = prixMin;
	}

	public BigDecimal getPrixMax() {
		return prixMax;
	}

	public void setPrixMax(BigDecimal prixMax) {
		this.prixMax = prixMax;
	}

	public String rechercheGlobal() throws Exception {

		log.info("Entrée dans la classe : " + getClass().getName());
		log.info("Entrée dans la méthode : rechercheGlobal()");
		log.info("getNomArticle() vaut : " + getNomArticle());
		log.info("getPrixMin() vaut : " + getPrixMin());
		log.info("getPrixMax() vaut : " + getPrixMax());

		String critNomArticle = getNomArticle();
		BigDecimal critPrixMin = getPrixMin();
		BigDecimal critPrixMax = getPrixMax();

		// Lors du premier affichage de la page, les champs sont vides: ne pas
		// lancer la requête.
		if ((getNomArticle().equals("")) && (null == getPrixMin())
				&& (null == getPrixMax())) {
			nomsArticles = rechercheNomsArticles();
			log.info("cas INPUT");
			return INPUT;

		} else {

			RechercheRemote rechercheRemote = (RechercheRemote) ServiceLocator
					.getInstance().getService(
							"VenteEnLigne/RechercheBean/remote");
			List _articles = rechercheRemote.rechercheArticles(critNomArticle,
					critPrixMin, critPrixMax);

			int taille = _articles.size();
			Article temp = new Article();

			for (int i = 0; i < taille; i++) {
				Object[] ligne = (Object[]) _articles.get(i);
				BigDecimal articleId = (BigDecimal) ligne[0];
				String nomArt = (String) ligne[1];
				BigDecimal prixArt = (BigDecimal) ligne[2];
				temp = new Article(articleId.longValue(), nomArt, prixArt
						.doubleValue());
				articles.add(i, temp);
			}

			int total = 0;
			if (null != articles) {
				log.info("articles non null");
				total = articles.size();
			}
			log.info("Le total est : " + total);
			log.info("cas SUCCESS");
			return SUCCESS;
		} // Fin else
	}

	public List rechercheNomsArticles() throws Exception {

		log.info("Entrée dans la classe : " + getClass().getName());
		log.info("Entrée dans la méthode : rechercheNomsArticles()");

		RechercheRemote rechercheRemote = (RechercheRemote) ServiceLocator
				.getInstance().getService("VenteEnLigne/RechercheBean/remote");
		List _articles = rechercheRemote.rechercheNomArticles();

		return _articles;
	}
}