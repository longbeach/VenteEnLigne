<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div style="width: 100%;  height: 100%; background-color: #ADDAA1; " >
<table>

<tr>
  <td>
    <h2>Menu</h2>
  </td>
</tr>

<tr>
  <td width="100" valign="top">
		<s:url id="accueil" action="accueilAdmin" />	<s:a href="%{accueil}" >Accueil<br>
		 <img src="<%=request.getContextPath()%>/images/menu_accueil.png"></s:a> 
  </td>
</tr>

<tr>
  <td width="100" valign="top">
  <s:url id="listeUtils" action="list"/><s:a href="%{listeUtils}">Gestion des clients <br>(Administrateurs et partiellement Gestionnaires)<br>
		 <img src="<%=request.getContextPath()%>/images/gestionClients.png"></s:a>  
  </td>
</tr>

<tr>
  <td width="100" valign="top">
   <s:url id="listeArts" action="listeArticles"/><s:a href="%{listeArts}">Gestion des articles<br> (Administrateurs et Gestionnaires)<br>
		 <img src="<%=request.getContextPath()%>/images/GestionArticles.gif"></s:a>  
  </td>
</tr>

<tr>
  <td width="100" valign="top">
    <a href="<%=request.getContextPath()%>/login/logout.jsp">Deconnexion<br>
 	<img src="<%=request.getContextPath()%>/images/deconnexion.gif" height="50" width="50"></a>
  </td>
</tr>

</table>
</div> 
