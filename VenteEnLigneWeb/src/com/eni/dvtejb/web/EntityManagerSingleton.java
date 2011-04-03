package com.eni.dvtejb.web;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerSingleton {

	private static EntityManager entityManager = null;

    public static EntityManager getInstance() {
      if ( entityManager == null) {
        EntityManagerFactory entityManagerFactory;
        entityManagerFactory = Persistence.createEntityManagerFactory("VenteEnLigneClientJavaSE");
        entityManager = entityManagerFactory.createEntityManager();
        return entityManager;
      }
      else
        return entityManager;
    }

}
