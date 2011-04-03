package com.eni.dvtejb.metier.sessions;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

@Stateless
@Local(CompteurStatelessLocal.class)
public class CompteurStatelessBean {
	
	private static final Logger log = Logger.getLogger(CompteurStatelessBean.class);
	
	private int compteur = 0;

	public int incrementer() {
        return ++compteur;
    }
    
    @PostConstruct
    public void raz() {
    	System.out.println("CompteurStatelessBean.PostConstruct");
    	log.info("Entree dans la methode raz() de CompteurStatelessBean");
        compteur = 99;
    }
}
