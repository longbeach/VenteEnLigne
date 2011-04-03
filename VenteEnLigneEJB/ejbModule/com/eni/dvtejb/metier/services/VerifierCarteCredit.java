package com.eni.dvtejb.metier.services;

/*
 * Cette classe est l'interface endpoint
 */
import java.rmi.Remote;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

@WebService
@SOAPBinding(style = Style.RPC, use=Use.LITERAL)
public interface VerifierCarteCredit extends Remote {
	
	public boolean validerCarteCredit(String typeCarteCredit, String numeroCartecredit);

}
