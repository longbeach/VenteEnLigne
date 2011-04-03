package com.eni.dvtejb.metier.services;

import java.util.HashMap;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ServiceLocator {
	
	// L'instance de cette classe est private pour en faire un singleton
	private static ServiceLocator serviceLocator = null;

	InitialContext context = null;

	// Gestion d'un cache sous la forme d'une HashMap
	HashMap cache = null;

	public ServiceLocator() throws NamingException {

		context = new InitialContext();
		// Initialisation  de la HashMap à 10 objets
		cache = new HashMap(10);
	}

	// Retourne l'instance singleton
	public synchronized static ServiceLocator getInstance() throws NamingException {
		
		if (serviceLocator == null) {
			serviceLocator = new ServiceLocator();
		}
		return serviceLocator;
	}


	public Object getService(String jndiName) throws NamingException {
		
		if (!cache.containsKey(jndiName)) {
		// Si l'objet n'est pas dans le cache, faire un lookup et le sauver
			cache.put(jndiName, context.lookup(jndiName));
		}
		return cache.get(jndiName);
	}

}
