package com.eni.dvtejb.metier.sessions;
import java.util.List;

import javax.ejb.Remote;

import com.eni.dvtejb.metier.entities.Article;

@Remote
public interface ArticleDAOBeanRemote {
	
	 public List<Article> rechercherTous();
	 public void sauver(Article article); 
	 public void supprimer(long id);
	 public Article rechercher(long id);
}
