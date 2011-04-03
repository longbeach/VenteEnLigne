<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<body>

<div align="right" >	
 <img src="<%=request.getContextPath()%>/images/utilisateur.gif" ><br>
 <s:text name="general.nom" /> : <s:property value="#session.leclient.nom" /><br>
 <s:text name="general.prenom" /> :  <s:property value="#session.leclient.prenom" />
</div>

<s:debug/>

<center>

<img src="<%=request.getContextPath()%>/images/INGDMYFS0341_petite.jpg"><br /><br />

<!--  Struts 2 transmet automatiquement les valeurs du bean  -->
<h1>
	<s:text name="accueil.bienvenue" > 
	    <s:param ><s:property value="#session.leclient.nom" /> </s:param>
		<s:param ><s:property value="#session.leclient.prenom" /> </s:param>
	</s:text>
</h1>

<s:text name="accueil.phrase1" /> :  <s:property value="#session.leclient.login" /><br> 

	<s:url id="edit" action="editionProfil"/><s:a href="%{edit}" ><s:property value="getText('accueil.lienProfil')"/></s:a> 
    - 
	<s:url id="affiche" action="afficheMagasin"/><s:a href="%{affiche}"><s:text name="accueil.lienListeArticles" /></s:a> 
    -
    <s:url id="historique" action="historiquecommandes"/><s:a href="%{historique}"><s:text name="accueil.lienHistorique" /></s:a> 
	- 
   <s:url id="recherche" action="recherche" /><s:a href="%{recherche}" ><s:text name="accueil.lienRecherche" /><br> </s:a>  

</center>
</body>
</html>
