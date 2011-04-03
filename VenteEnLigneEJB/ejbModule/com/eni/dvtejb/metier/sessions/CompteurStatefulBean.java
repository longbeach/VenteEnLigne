package com.eni.dvtejb.metier.sessions;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateful;

import org.apache.log4j.Logger;

@Stateful
@Local(CompteurStatefulLocal.class)
public class CompteurStatefulBean {
	
	private static final Logger log = Logger.getLogger(CompteurStatefulBean.class);
	private int compteur = 0;

    public int incrementer() {
        return ++compteur;
    }

    @PostConstruct
    public void  raz() {
    	System.out.println("CompteurStatefulBean.PostConstruct");
    	log.info("Entree dans la methode raz() de CompteurStatefulBean"); 
        compteur = 99;
    }
}
