package com.eni.dvtejb.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.query.JRJpaQueryExecuterFactory;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

import org.apache.log4j.Logger;

public class RapportGraphiqueJasperServlet  extends HttpServlet {
	private static final long serialVersionUID = 1L;	
	private static final Logger log = Logger.getLogger(RapportGraphiqueJasperServlet.class);
    
    public RapportGraphiqueJasperServlet() {    	
        super();
    }
    
	public void service(HttpServletRequest request,	HttpServletResponse response) 
		throws IOException, ServletException
		{
		log.info("------------------------- Debut service -------------------------");
		ServletContext context = this.getServletConfig().getServletContext();
		
		// 1.Compilation du fichier .jrxml en .jasper		
		try
		{
			log.info("------------------------- Debut Compilation du fichier .jrxml -------------------------");
			JasperCompileManager.compileReportToFile(context.getRealPath("/rapports/RapportGraphique.jrxml"));
			log.info("------------------------- Fin Compilation du fichier .jrxml -------------------------");		
		}
		catch (JRException e)
		{			
			PrintWriter out = response.getWriter();
			response.setContentType("text/html");						
			out.println("<html>");
			out.println("<head>");
			out.println("<title>JasperReports - Erreur</title>");
			out.println("</head>");
			out.println("<body bgcolor=\"white\">");
			out.println("<span class=\"bnew\">JasperReports a rencontré une erreur :</span>");
			out.println("<pre>");
			e.printStackTrace(out);
			out.println("</pre>");
			out.println("</body>");
			out.println("</html>");
			return;
		}		
		
		// 2.Remplissage du fichier .jasper avec les données de la base. Genere un fichier .jrprint
		JasperPrint jasperPrint = null;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("VenteEnLigneModuleEJB", new HashMap());
		EntityManager em = emf.createEntityManager();
		
	try	{
			log.info("------------------------- Debut remplissage du fichier et generation du fichier .jrprint");
			
			log.info("Remplissage du rapport JPRINT...");
	Map parametresMap =  new HashMap();
	parametresMap.put(JRJpaQueryExecuterFactory.PARAMETER_JPA_ENTITY_MANAGER, em);
	String reportFileName = context.getRealPath("/rapports/RapportGraphique.jasper");

	log.info("Avant fillReport");		 		
	jasperPrint = JasperFillManager.fillReport(reportFileName, parametresMap);		
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
					
		//3. Export du fichier .jrprint au format PDF 			

		ServletOutputStream out2 = response.getOutputStream();
		try
		{
			log.info("------------------------- Debut export PDF -------------------------");
			JRPdfExporter exporterPDF = new JRPdfExporter();
			request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
			exporterPDF.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			log.info("Avant exporterPDF");		
			jasperPrint.getName();
			exporterPDF.setParameter(JRExporterParameter.OUTPUT_STREAM, out2); 
			response.setContentType("application/pdf");         
			response.setHeader("Content-disposition", "filename=" + "RapportGraphique.pdf;");
    		exporterPDF.exportReport();
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
			out2.print(stringWriter.toString());			
			e.printStackTrace();
		}		
	}
}
