package com.eni.dvtejb.jasper;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.query.JRJpaQueryExecuterFactory;

public class ListeArticlesJasperCamembert {
	public static void main(String[] args) 
	{		
		// Compilation
		String resultat = 
			compileReport("K:/ENI/DeveloppementEJB3/workspaceEclipse/VenteEnLigneClient/appClientModule/com/eni/dvtejb/jasper/",
					"ListeArticlesCamembert");

		try {
			long start = System.currentTimeMillis();
			
				// création de l'entity manager factory pour la connexion avec la base de données
				EntityManagerFactory emf = Persistence.createEntityManagerFactory("VenteEnLigneClient", new HashMap());
				EntityManager em = emf.createEntityManager();
				
				try
				{
					Map parameters = getParameters(em);					
					JasperRunManager.runReportToPdfFile("K:/ENI/DeveloppementEJB3/workspaceEclipse/VenteEnLigneClient/appClientModule/com/eni/dvtejb/jasper/ListeArticlesCamembert.jasper", parameters);					
					em.close();
					
					System.err.println("Durée de génération du fichier PDF : " + (System.currentTimeMillis() - start));
				}
				finally
				{
					if (em.isOpen())
						em.close();
					if (emf.isOpen())
						emf.close();
				}
		}
		catch (JRException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	 // Paramètres du rapport
	private static Map getParameters(EntityManager em) {
		Map parameters = new HashMap();
		parameters.put(JRJpaQueryExecuterFactory.PARAMETER_JPA_ENTITY_MANAGER, em);
		parameters.put("TitreRapport", "Camembert");		
		return parameters;
	}
	
	// Compilation du fichier .jrxml en un binaire .jasper
	public static String compileReport(String path, String reportName){
		
		try {
			// compileReportToFile(fichier source, fichier destination)
			JasperCompileManager.compileReportToFile(path+reportName+".jrxml", path+reportName+".jasper");					
		} catch(Exception e){
			e.printStackTrace();
			return "erreur";
		}		
		return "succes";			
	}
}
