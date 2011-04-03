package com.eni.dvtejb.clientStruts2.action;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.ImageIcon;

import net.sf.jasperreports.engine.JasperCompileManager;

import org.apache.struts2.ServletActionContext;
import org.jboss.logging.Logger;

import com.eni.dvtejb.metier.entities.Article;
import com.eni.dvtejb.metier.services.ServiceLocator;
import com.eni.dvtejb.metier.sessions.PanierBeanRemote;
import com.opensymphony.xwork2.ActionSupport;

public class JasperArticleAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(JasperArticleAction.class);
	
	// La dataSource du rapport
    private Article artDetails;

    public Article getArtDetails() {
		return artDetails;
	}
    
    // Les paramètres du rapport
    private HashMap paramsRapport = new HashMap();
    
    public HashMap getParamsRapport() {
        return paramsRapport;
    }

	public String execute() throws Exception {

	  log.info("Debut méthode execute()");
	  
      String numeroId = (String)ServletActionContext.getRequest().getParameter("numeroId");
  	  long l = Long.parseLong(numeroId);
    	 
      PanierBeanRemote panierBeanRemote = (PanierBeanRemote) ServiceLocator.getInstance().getService("VenteEnLigne/PanierBean/remote");
      artDetails = panierBeanRemote.findById(l); 
      
	  Image imageJasper = Toolkit.getDefaultToolkit().createImage(artDetails.getImage());
	  imageJasper = new ImageIcon(imageJasper).getImage();	  
	  BufferedImage bufferedImage = new BufferedImage(imageJasper.getWidth(null),imageJasper.getHeight(null),BufferedImage.TYPE_INT_RGB );
	   
      paramsRapport.put("TitreRapport", "Fiche Article");  
      paramsRapport.put("SummaryImage", imageJasper);

        try {
          JasperCompileManager.compileReportToFile(
        		  ServletActionContext.getServletContext().getRealPath("/rapports/ficheArticle.jrxml"),
        		  ServletActionContext.getServletContext().getRealPath("/rapports/ficheArticle.jasper"));        	
        	
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
        return SUCCESS;
    }
}
