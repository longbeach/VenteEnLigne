package com.eni.dvtejb.clientJSF2.validators;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.apache.log4j.Logger;

@FacesValidator("verificationEmailClient")
public class EmailValidator implements Validator {
	
	private static final Logger log = Logger.getLogger(EmailValidator.class);
	 
	// messageBundleName récupère la valeur de l'attribut <message-bundle> dans faces-config.xml
	 private static String messageBundleName =
		        FacesContext.getCurrentInstance().getApplication().getMessageBundle();

	 public FacesMessage getMessage(FacesContext facesContext, String message) {
		 log.info("Entree dans la methode getMessage");		 		 
		 log.info("messageBundleName vaut : " + messageBundleName);
		 
		 ResourceBundle rb =
		 ResourceBundle.getBundle(messageBundleName, facesContext.getViewRoot().getLocale());
	
		 String msg = rb.getString(message);
		 FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_FATAL, msg, msg);
		 	return facesMsg;
	 }

	public void validate(FacesContext facesContext, UIComponent uiComponent, Object value)
	throws ValidatorException {
		log.info("Entree dans la methode de validation");
		
		String email = (String) value;
		//Le pattern de l'adresse email
        Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");
        
        //Création d'un matcher à partir de l'email avec le pattern
        Matcher ma = pattern.matcher(email);
        
        //Est-ce que l'email matche le pattern ?
        boolean match = ma.matches();
		
		if (!match) {
				throw new ValidatorException(getMessage(facesContext, "validator_MauvaisEmail"));			
		}	
	}
}
