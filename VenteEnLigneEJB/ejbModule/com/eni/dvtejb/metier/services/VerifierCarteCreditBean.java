package com.eni.dvtejb.metier.services;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;

/*
 * Cette classe est le bean endpoint
 */
@Stateless
@WebService(endpointInterface ="com.eni.dvtejb.metier.services.VerifierCarteCredit")
@Remote(VerifierCarteCredit.class)
public class VerifierCarteCreditBean {
	
	public VerifierCarteCreditBean(){		
	}
	
	@WebMethod(operationName="verifCarteCredit")
	public boolean validerCarteCredit(String typeCarteCredit, String numeroCarteCredit){
		
		if ("VISA".equals(typeCarteCredit)){
			if ((numeroCarteCredit.length() != 13 && numeroCarteCredit.length() != 16) ||
	                 Integer.parseInt(numeroCarteCredit.substring(0, 1)) != 4)
	         {
		                return false;
		     }
			
		} else if ("MASTERCARD".equals(typeCarteCredit))
		{
			 if (numeroCarteCredit.length() != 16 ||
		                Integer.parseInt(numeroCarteCredit.substring(0, 2)) < 51 ||
		                Integer.parseInt(numeroCarteCredit.substring(0, 2)) > 55)
	         {
		                return false;
		     }
		} else if ("AMEX".equals(typeCarteCredit)) {
			if (numeroCarteCredit.length() != 15 ||
	                (Integer.parseInt(numeroCarteCredit.substring(0, 2)) != 34 &&
	                    Integer.parseInt(numeroCarteCredit.substring(0, 2)) != 37))
	            {
	            }
		}
		 return validerLuhn(numeroCarteCredit);
	}
	
	// Tous les numéros de carte de crédit doivent être validés par l'algorithme de Luhn
    private boolean validerLuhn(String numeroString) {
    	
        char[] charTableau = numeroString.toCharArray();
        int[] numero = new int[charTableau.length];
        int total = 0;
		
        for (int i=0; i < charTableau.length; i++) {
        	numero[i] = Character.getNumericValue(charTableau[i]);
        }
		
        for (int i = numero.length-2; i > -1; i-=2) {
        	numero[i] *= 2;
			
            if (numero[i] > 9)
            	numero[i] -= 9;
        }
		
        for (int i=0; i < numero.length; i++)
            total += numero[i];
		
            if (total % 10 != 0)
                return false;
		
        return true;
    }
}
