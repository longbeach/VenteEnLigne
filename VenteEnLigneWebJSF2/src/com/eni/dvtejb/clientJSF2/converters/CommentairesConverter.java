package com.eni.dvtejb.clientJSF2.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.log4j.Logger;

@FacesConverter(value ="commentairesConverter")
public class CommentairesConverter implements Converter {
	
	private static final Logger log = Logger.getLogger(CommentairesConverter.class);

	// M�thode appel�e lors de la phase d'application des param�tres de la requ�te
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String newValue) {
		log.info("Appel de la m�thode getAsObject()");
		String commentairesConvertis = null;
		if (newValue == null){
			return newValue;
		}
		commentairesConvertis = newValue;
		commentairesConvertis = commentairesConvertis.replace(".", ".\n"); 	
		return commentairesConvertis;
	}
	
	// M�thode appel�e lors de la phase de restitution de la r�ponse
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object newValue) {
		log.info("Appel de la m�thode getAsString()");
		return newValue.toString();
	}	
}
