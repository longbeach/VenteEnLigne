package com.eni.dvtejb31.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eni.dvtejb31.ejb.sessionbeans.ContactServiceBean;

public class GestionContacts extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB
    private ContactServiceBean listeContactsBean;
 
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GestionContacts</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>" + listeContactsBean.renvoyerContacts() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally { 
            out.close();
        }
    }
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 	
}
