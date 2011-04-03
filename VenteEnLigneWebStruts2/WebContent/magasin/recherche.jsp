<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<link href="<s:url value="/css/styles.css"/>" rel="stylesheet" type="text/css"/>
<html>
<title>Recherche d'articles</title>
<sx:head  debug="true" />

<div align="right" >	
 <img src="<%=request.getContextPath()%>/images/utilisateur.gif" ><br>
 <s:text name="general.nom" /> : <s:property value="#session.leclient.nom" /><br>
 <s:text name="general.prenom" /> :  <s:property value="#session.leclient.prenom" />
</div>

<center>
<h1>Recherche d'articles  <img src="<%=request.getContextPath()%>/images/recherche.gif"></h1>

<s:form	action="recherche" method="post" theme="xhtml" tooltipConfig="#{'tooltipIcon':'/images/info.png'}" >

 <table class="table1">
	<sx:autocompleter label="Nom" list="nomsArticles" name="nomArticle" tooltip="Rentrez le nom de l'article recherché" 
	autoComplete="true"	value="Tous"/>
	<s:textfield name="prixMin"   label="Prix minimum" value="0" tooltip="Rentrez le prix minimum souhaité"/>
	<s:textfield name="prixMax"  label="Prix maximum"  value="10000" tooltip="Rentrez le prix maximum souhaité"/>

	<s:submit  value="Recherche" align="center"/> 	<s:reset  value="Reset" align="center"/> 
  </table>

</s:form>
</center>
</html>