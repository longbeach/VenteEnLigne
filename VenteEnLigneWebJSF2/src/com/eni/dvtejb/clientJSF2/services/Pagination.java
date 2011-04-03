package com.eni.dvtejb.clientJSF2.services;

import java.io.Serializable;

import org.jboss.logging.Logger;

public class Pagination implements Serializable {
	
	private static final Logger log = Logger.getLogger(Pagination.class);
	
	private int nombreArticles = 0; // nombre d'articles en base. Ce nombre d'articles est setté dans MagasinBean au démarrage (constructeur)
    private int nombreLignes = 6;    // le nombre de lignes (articles) que l'on souhaite afficher par page
    private int premierArticle = 0;  // le numéro du premier article à afficher, parmi la liste des articles retournées
   
    public int getNombreLignes() {
        return nombreLignes;
    }

    public int getNombreArticles() {
        return nombreArticles;
    }

    public void setNombreArticles(int nombreArticles) {
        this.nombreArticles = nombreArticles;
    }


    public int getPremierArticle() {
    	log.info(" -------------- Debut getPremierArticle() -------------");
        return premierArticle;
    }

    public void setPremierArticle(int premierArticle) {
        this.premierArticle = premierArticle;
    }

    public int getDernierArticle() {
        int dernierArticle = 0;
        if ((premierArticle + nombreLignes) > nombreArticles ){
        	dernierArticle = nombreArticles;
        } else {
        	dernierArticle = premierArticle + nombreLignes;
        }
        return dernierArticle;
    }

    public void pageSuivante() {
    	log.info("pageSuivante===> premierArticle vaut : " + premierArticle);
        if ((premierArticle + nombreLignes) < nombreArticles) {
        	premierArticle = premierArticle + nombreLignes;
        }
    }

    public void pagePrecedente() {
    	log.info("pagePrecedente===> premierArticle vaut : " + premierArticle);
        premierArticle = premierArticle - nombreLignes;
        if (premierArticle < 0) {
        	premierArticle = 0;
        }
    }

    public boolean getExisteArticlesSuivants() {
    	boolean existe = false;
        if (getDernierArticle() < getNombreArticles()) {
        	existe = true;
        } 
        return existe;
    }

    public boolean getExisteArticlesPrecedents() {
    	boolean existe = false;
        if (premierArticle >= nombreLignes) {
        	existe = true;
        }  
        return existe;
    }
}
