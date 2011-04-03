<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div style="width: 100%;  height: 100%; background-color: #ADDAA1" >
<table>

<tr>
  <td>    <strong><s:text name="menu.titre" /></strong>  </td>
</tr>

<tr>
  <td width="100" valign="top">
		<s:url id="accueil" action="accueil" />	
		<s:a href="%{accueil}" ><strong><s:text name="menu.accueil" /><br>
		<img src="${pageContext.request.contextPath}/images/menu_accueil.png">
		</s:a> 
  </td>
</tr>

<tr>
  <td width="100" valign="top">
   <s:url id="profil" action="editionProfil" />	
   <s:a href="%{profil}" ><strong><s:text name="menu.profil" /><br>
  	 <img src="<%=request.getContextPath()%>/images/portrait.jpg" height="50" width="50">
   </s:a>  
  </td>
</tr>

<tr>
  <td width="100" valign="top">
    <s:url id="magasin" action="afficheMagasin" />	
    <s:a href="%{magasin}" ><strong><s:text name="menu.articles" /><br>
  	 <img src="<%=request.getContextPath()%>/images/etageres.jpg">
  	 </s:a>  
  </td>
</tr>

<tr>
  <td width="100" valign="top">
     <s:url id="historique" action="historiquecommandes" />	
     <s:a href="%{historique}" ><s:text name="menu.historique" /><br>
  	 <img src="<%=request.getContextPath()%>/images/historique.gif">
  	 </s:a>  
  </td>
</tr>

<tr>
  <td width="100" valign="top">
     <s:url id="recherche" action="recherche" />	
     <s:a href="%{recherche}" ><s:text name="menu.recherche" /><br>
  	 <img src="<%=request.getContextPath()%>/images/recherche.gif">
  	 </s:a>  
  </td>
</tr>

<tr>
  <td width="100" valign="top">
     <s:url id="question" action="question" />	
     <s:a href="%{question}" ><s:text name="menu.contact" /><br>
  	 <img src="<%=request.getContextPath()%>/images/question.png">
  	 </s:a>  
  </td>
</tr>

<tr>
  <td width="100" valign="top">
    <a href="<%=request.getContextPath()%>/login/logout.jsp"><s:text name="menu.deconnexion" /><br>
 	<img src="<%=request.getContextPath()%>/images/deconnexion.gif" height="50" width="50">
	</a>
  </td>
</tr>

</table>
</div> 
