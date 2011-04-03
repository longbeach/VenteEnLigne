package com.eni.dvtejb31.singletons;

import java.util.concurrent.atomic.AtomicInteger;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;

@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class SingletonB {
    private AtomicInteger compteur;

    public int getCompteur() {
        return compteur.get();
    }
    
    public void incrementer() {
        compteur.incrementAndGet();
    }
}
