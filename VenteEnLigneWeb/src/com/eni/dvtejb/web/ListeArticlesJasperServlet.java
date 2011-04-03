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
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.query.JRJpaQueryExecuterFactory;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

import org.apache.log4j.Logger;

public class ListeArticlesJasperServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;	
	private static final Logger log = Logger.getLogger(ListeArticlesJasperServlet.class);
    
    public ListeArticlesJasperServlet() {    	
        super();
    }
    
	public void service(HttpServletRequest request,	HttpServletResponse response) 
		throws IOException, ServletException
		{
	
		log.info("------------------------- Debut service -------------------------");
		ServletContext context = this.getServletConfig().getServletContext();
		
		String typeRapport = request.getParameter("typeRapport");		
		log.info("typeRapport vaut : " + typeRapport);
		String leCatalogue = request.getParameter("leCatalogue");
		
		
		//JasperReport jasperReport = null;
		
		// 1.Compilation du fichier .jrxml en .jasper		
		try
		{
			log.info("------------------------- Debut Compilation du fichier .jrxml -------------------------");
		
			JasperCompileManager.compileReportToFile(context.getRealPath("/rapports/ListeArticlesCritereCatalogueRapport.jrxml"));
//			JasperDesign jasperDesign = JRXmlLoader.load(context.getRealPath("/rapports/ListeArticlesCritereCatalogueRapport.jrxml"));
//			jasperReport = JasperCompileManager.compileReport(jasperDesign);
			
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
	
	parametresMap.put("TitreRapport", "Liste des articles en base");
	parametresMap.put("NomCatalogue", leCatalogue);
	
	String reportFileName = context.getRealPath("/rapports/ListeArticlesCritereCatalogueRapport.jasper");

	log.info("Avant fillReport");		 		
	jasperPrint = JasperFillManager.fillReport(reportFileName, parametresMap);		
	// jasperPrint = JasperFillManager.fillReport(jasperReport, parametresMap);				
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
					
		//3. Export du fichier .jrprint au format choisi : HTML / PDF / Excel / RTF / Word 2007				
		
	if ("pageHTML".equals(typeRapport)){
		PrintWriter out = response.getWriter();
		try 
		{			
			log.info("------------------------- Debut export HTML -------------------------");
			JRHtmlExporter exporter = new JRHtmlExporter();
			request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
			exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "image?image=");
			log.info("Avant JRHtmlExporter");
			exporter.exportReport();
			log.info("Apres JRHtmlExporter");
	}
		catch (JRException e)
		{
			log.info("------------------------- JRException dans export HTML ------------------------- ");
			// affichage de la stack trace dans le browser
			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);
			e.printStackTrace(printWriter);
			response.setContentType("text/plain");
			out.print(stringWriter.toString());			
			e.printStackTrace();
		}
	}
	if ("pdf".equals(typeRapport)){
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
			response.setHeader("Content-disposition", "filename=" + "listeArticles.pdf;");
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
			out2.print(stringWriter.toString());			
			e.printStackTrace();
		}		
	}
	if ("xls".equals(typeRapport)){
		 ServletOutputStream out2 = response.getOutputStream();
			try
			{
				log.info("------------------------- Debut export XLS -------------------------");
				JExcelApiExporter exporterXLS = new JExcelApiExporter();
				request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
				exporterXLS.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);				
				exporterXLS.setParameter(JRExporterParameter.OUTPUT_STREAM, out2); 
				response.setContentType("application/msexcel");         
				response.setHeader("Content-disposition", "filename="  +  "listeArticles.xls;");			 				 
				log.info("Avant exporterXLS");
				exporterXLS.exportReport();
				log.info("Apres exporterXLS");
			}
			catch (JRException e)
			{
				log.info("------------------------- JRException dans export XLS ------------------------- ");
				// affichage de la stack trace dans le browser
				StringWriter stringWriter = new StringWriter();
				PrintWriter printWriter = new PrintWriter(stringWriter);
				e.printStackTrace(printWriter);
				response.setContentType("text/plain");
				out2.print(stringWriter.toString());				
				e.printStackTrace();
			}
	}
	if ("rtf".equals(typeRapport)){
		 ServletOutputStream out2 = response.getOutputStream();
			try
			{
				log.info("------------------------- Debut export RTF -------------------------");
				
				JRRtfExporter exporterRTF  = new JRRtfExporter();
				request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
				exporterRTF.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);				
				exporterRTF.setParameter(JRExporterParameter.OUTPUT_STREAM, out2); 
				response.setContentType("application/rtf");         
				response.setHeader("Content-disposition", "filename="  +  "listeArticles.rtf;");				 				 
				log.info("Avant exporterRTF");
				exporterRTF.exportReport();
				log.info("Apres exporterRTF");
			}
			catch (JRException e)
			{
				log.info("------------------------- JRException dans export RTF ------------------------- ");
				// affichage de la stack trace dans le browser
				StringWriter stringWriter = new StringWriter();
				PrintWriter printWriter = new PrintWriter(stringWriter);
				e.printStackTrace(printWriter);
				response.setContentType("text/plain");
				out2.print(stringWriter.toString());				
				e.printStackTrace();
			}
	}
	
	if ("word2007".equals(typeRapport)){
		 ServletOutputStream out2 = response.getOutputStream();
			try
			{
				log.info("------------------------- Debut export WORD 2007 -------------------------");
				
				JRDocxExporter exporterDocx = new JRDocxExporter();
				request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
				exporterDocx.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);				
				exporterDocx.setParameter(JRExporterParameter.OUTPUT_STREAM, out2); 
				response.setContentType("application/vnd.ms-word.document.12");         
				response.setHeader("Content-disposition", "filename=" +  "listeArticles.docx;");				 				 
				log.info("Avant exporterWord2007");
				exporterDocx.exportReport();
				log.info("Apres exporterWord2007");
			}
			catch (JRException e)
			{
				log.info("------------------------- JRException dans export  WORD 2007 ------------------------- ");
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
}
