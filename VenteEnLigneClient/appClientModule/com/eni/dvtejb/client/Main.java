package com.eni.dvtejb.client;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Main {
	
	public static void main(String[] argv) throws Exception {		
		Date d = stringToDate("2009-11-24T00:00:00+01:00");	    
	    System.out.println("d vaut : " + d);	    
	  }
	
	 public static Date stringToDate(String sDate) throws ParseException {		 	 
		  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");	
	      return formatter.parse(sDate);
	 }

}
