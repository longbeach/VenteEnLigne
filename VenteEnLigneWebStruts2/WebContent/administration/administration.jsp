<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
<body>

<div align="right" >	
 <img src="<%=request.getContextPath()%>/images/utilisateur.gif" ><br>
 <s:text name="general.nom" /> : <s:property value="#session.lutilisateur.nom" /><br>
 <s:text name="general.prenom" /> :  <s:property value="#session.lutilisateur.prenom" />
</div>

<s:debug/>
<s:actionerror />
<center>

<img src="<%=request.getContextPath()%>/images/siteadministration.gif"><br /><br />
 Interface d'administration <br /><br />
    <s:url id="listeUtils" action="list"/><s:a href="%{listeUtils}">Gestion des clients (Administrateurs et partiellement Gestionnaires)</s:a>  
    - 
    <s:url id="listeArts" action="listeArticles"/><s:a href="%{listeArts}">Gestion des articles (Administrateurs et  Gestionnaires)</s:a>
<br/>

</center>
</body>
</html>