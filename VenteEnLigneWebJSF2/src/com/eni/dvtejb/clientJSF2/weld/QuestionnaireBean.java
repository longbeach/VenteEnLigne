package com.eni.dvtejb.clientJSF2.weld;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

@Named("questionnaireVenteEnLigne")
public class QuestionnaireBean {
	
	private static final Logger log = Logger.getLogger(QuestionnaireBean.class);
	
	@Inject	@QuestionHasard
	private Question question;
	
	private String reponse;
	
	public String getReponse() {
		return reponse;
	}

	public void setReponse(String reponse) {
		this.reponse = reponse;
	}
 
	@PostConstruct
    public void init() {
        log.info(QuestionnaireBean.class.getSimpleName() + " a été instancié.");
    }
	
	public String getQuestionAleatoire() {
		return question.laQuestion;
	}

	// sauver la réponse en base	
	public void sauverReponse(){			
		addMessage("Merci, votre réponse est prise en compte.");
	}
	
	public static void addMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext fc = FacesContext.getCurrentInstance();
        fc.addMessage(null, facesMsg);
    }
	
}
