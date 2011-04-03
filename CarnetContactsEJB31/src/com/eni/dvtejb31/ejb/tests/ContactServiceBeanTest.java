package com.eni.dvtejb31.ejb.tests;

import static org.junit.Assert.assertNotNull;

import javax.ejb.embeddable.EJBContainer;
import javax.naming.Context;
import javax.naming.NamingException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.eni.dvtejb31.ejb.entitybeans.Contact;
import com.eni.dvtejb31.ejb.sessionbeans.ContactServiceBean;

public class ContactServiceBeanTest {
	
	private static EJBContainer container;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		 container = EJBContainer.createEJBContainer();
	}
	
	@Test
	  public void hello() throws NamingException{
	   
	    Context ctx = container.getContext();
	    ContactServiceBean contactServiceBean = 
	    	(ContactServiceBean) ctx.lookup("java:global/CarnetContactsEJB31/ContactServiceBean");
	    assertNotNull(contactServiceBean);
	    Contact contact = contactServiceBean.rechercher(0);
	    assertNotNull(contact);
	    container.close();
	  }
	
	@AfterClass
	public static void tearDownClass() throws Exception {
		container.close();
	}
}
