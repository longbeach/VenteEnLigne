package com.eni.dvtejb31.singletons;

import javax.ejb.AccessTimeout;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Singleton;

import org.jboss.logging.Logger;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
public class SingletonA {

	private static final Logger log = Logger.getLogger(SingletonA.class);
	
	private int compteur;

    @Lock(LockType.READ)
    @AccessTimeout(300000)  // 5 mn
    public int getCompteur() {
        return compteur;
    }
       
    @Lock(LockType.WRITE)
    public void incrementer() {
        compteur++;
    }

}
