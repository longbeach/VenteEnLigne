package com.eni.dvtejb.client;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceFactory;

import com.eni.dvtejb.metier.services.VerifierCarteCredit;

public class ServiceClientStandaloneRPC {
	
    // @WebServiceRef(wsdlLocation="http://127.0.0.1:8085/VenteEnLigne-VenteEnLigneEJB/VerifierCarteCreditBean?wsdl")
    // static   VerifierCarteCredit service;	
	public static void main(String[] args) throws Exception {	
		//Methode JAX-WS		

        URL url = new URL("http://127.0.0.1:8085/VenteEnLigne-VenteEnLigneEJB/VerifierCarteCreditBean?wsdl");
        QName qname = new QName("http://services.metier.dvtejb.eni.com/", "VerifierCarteCreditBeanService");

        ServiceFactory factory = ServiceFactory.newInstance();
        Service remote = factory.createService(url, qname);

        VerifierCarteCredit service = (VerifierCarteCredit) remote.getPort(VerifierCarteCredit.class);
   		
       // System.out.println("Receiving: " + proxy.validerCarteCredit( "4716718496946025"));
        
        boolean isValide = false ;
        String numCC = "4716718496946025";
        isValide = service.validerCarteCredit( "VISA", numCC);
        
        if (isValide) {
        	System.out.println("Le numero de carte de credit " + numCC + " est valide");
        } else {
        	System.out.println("Le numero de carte de credit " + numCC + " est invalide");
        }
        
        //System.out.println("Receiving: " + service.validerCarteCredit( "VISA", "4111111111111111"));
    }
}
