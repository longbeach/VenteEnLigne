<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="javax.persistence.*,java.util.*,com.eni.dvtejb.web.*"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Requ�teur</title>
</head>
<body>
<center>
  <h1>Requ�teur JPQL</h1>
        
        <form action="requeteur.jsp" method="post">
            <b>Tapez la requ�te : </b> <br/>
            <textarea name="requete" rows="10"  cols="70">${param.requete}</textarea><br/>
            <input type="submit" value="Lancer la requ�te JPQL"/>
        </form>

        <p/>
        <%
          String requete = request.getParameter("requete");
          if ( requete == null || requete.length() ==  0) {
              return;
          }
          
          EntityManager em = EntityManagerSingleton.getInstance();
          List liste = null;
          try {
            Query  req = em.createQuery(requete);
            liste = req.getResultList();
          }
          catch(Exception ex) {
              out.println("<p/>Erreur :  " + ex.getMessage());
              return;
          }

          out.println("<h2>Resultat </h2><table border='2'");
          
          for (Object obj : liste) {
              out.println("<tr>");
              if (obj instanceof Object[]) {
                  for( Object o : (Object[]) obj)
                       out.println( "<td>"  + o  + "</td>");
              }
              else {
                out.println("<td>" + obj.toString() + "</td>");
              }  
              out.println("</tr>");
          }
        %>
</center>
</body>
</html>