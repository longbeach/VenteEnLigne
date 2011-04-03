package com.eni.dvtejb.clientJSF2.weld;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Produces;

public class Questions {
	

	List<String> questions;
	
	 public Questions() {
         // Dans la pratique, les questions sont soumises via un CMS et stockées en base
		 // dans une table dédiée.
		 // Mais pour des raisons de simplicité, elles sont dans la classe.
         questions = new ArrayList<String>();
         questions.add(0, "Etes-vous satisfait la liste des articles proposés ?");
         questions.add(1, "Allez-vous commander sur notre site dans les prochains jours ?");
         questions.add(2, "Quel(s) autre(s) catalogue(s) souhaitez-vous voir sur le site ?");
         questions.add(3, "Etes-vous satisfait de l'ergonomie du site ?");        
     }

	 @Produces @QuestionHasard
	 public Question questionHasard() {
		 Question q = new Question();
         int nb;
         nb = (int)(Math.random()*questions.size());
         q.laQuestion = questions.get(nb);
         return q;
	 }
}
