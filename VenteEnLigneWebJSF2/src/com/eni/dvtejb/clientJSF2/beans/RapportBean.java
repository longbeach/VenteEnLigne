package com.eni.dvtejb.clientJSF2.beans;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.query.JRJpaQueryExecuterFactory;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

import org.apache.log4j.Logger;

@ManagedBean
@SessionScoped
public class RapportBean {
	
	private static final Logger log = Logger.getLogger(RapportBean.class);

	public void genererRapport(ActionEvent actionEvent) 
	throws ClassNotFoundException, SQLException, IOException, JRException
	{

		FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse)facesContext.getExternalContext().getResponse();
		HttpServletRequest request = (HttpServletRequest)facesContext.getExternalContext().getRequest();
		JasperPrint jasperPrint = null;
		
		InputStream reportStream = 
			facesContext.getExternalContext().getResourceAsStream("/rapports/tableauCroise.jasper");
		ServletOutputStream servletOutputStream = response.getOutputStream();
		
		facesContext.responseComplete();
		
		// création de l'entity manager factory pour la connexion avec la base de données
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("VenteEnLigneModuleEJB", new HashMap());
		EntityManager em = emf.createEntityManager();
		
		try	{
			log.info("------------------------- Debut remplissage du fichier et generation du fichier .jrprint");
			
			log.info("Remplissage du rapport JPRINT...");
			Map parametresMap =  new HashMap();
			parametresMap.put(JRJpaQueryExecuterFactory.PARAMETER_JPA_ENTITY_MANAGER, em);
	
			log.info("Avant fillReport");	
			
			jasperPrint = JasperFillManager.fillReport(reportStream, parametresMap);							
			log.info("Apres fillReport");
			log.info("Fin remplissage du fichier et generation du fichier .jrprint...");
			log.info("-------------------------Fin remplissage du fichier et generation du fichier .jrprint");
		
			}
			catch (JRException e)
			{
				e.printStackTrace();
			}				
			finally
			{
				if (em.isOpen()) {em.close();}
				if (emf.isOpen()) {emf.close();}
			}
		
			try
			{
				log.info("------------------------- Debut export PDF -------------------------");
				JRPdfExporter exporterPDF = new JRPdfExporter();
				request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
				exporterPDF.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
				log.info("Avant exporterPDF");		
				jasperPrint.getName();
				exporterPDF.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream); 
				response.setContentType("application/pdf");         
				response.setHeader("Content-disposition", "filename=" + "tableauCroise.pdf;");
	    		exporterPDF.exportReport();
	    		log.info("Apres exporterPDF");	
				log.info("------------------------- Fin export PDF------------------------- ");
			}
			catch (JRException e)
			{
				log.info("------------------------- JRException dans export PDF ------------------------- ");
				// affichage de la stack trace dans le browser
				StringWriter stringWriter = new StringWriter();
				PrintWriter printWriter = new PrintWriter(stringWriter);
				e.printStackTrace(printWriter);
				response.setContentType("text/plain");
				servletOutputStream.print(stringWriter.toString());			
				e.printStackTrace();
			}	
				
		servletOutputStream.flush();
		servletOutputStream.close();
	}	
}
