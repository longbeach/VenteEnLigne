package com.eni.dvtejb.clientJSF2.pojos;

import javax.inject.Named;

@Named
public class MessageServerBean {
	public String getMessage() {  
		 return "Bonjour !";  
	}

}
