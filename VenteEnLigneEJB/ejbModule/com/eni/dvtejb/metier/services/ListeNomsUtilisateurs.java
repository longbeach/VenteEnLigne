package com.eni.dvtejb.metier.services;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ListeNomsUtilisateurs {
	
	@XmlElement
    protected java.util.List<String> nomsUtilisateurs;
		 
    public ListeNomsUtilisateurs() {     	
    	nomsUtilisateurs = new ArrayList<String>(0);
    }
}
