<%@ taglib prefix="s" uri="/struts-tags" %>

<link href="<s:url value="/css/styles.css"/>" rel="stylesheet" type="text/css"/>

<div align="right" >	
 <img src="<%=request.getContextPath()%>/images/utilisateur.gif" ><br>
 <s:text name="general.nom" /> : <s:property value="#session.leclient.nom" /><br>
 <s:text name="general.prenom" /> :  <s:property value="#session.leclient.prenom" />
</div>

<center>
<h1><s:text name="profil.titrePage" /></h1>

<a href="accueil.action"><s:text name="profil.retour" /></a> 
<br>

<s:form action="sauveProfil" method="POST" theme="xhtml"  >

<table class="table1">

 <s:actionerror />
 <s:fielderror />

<tr><s:textfield  name ="client.prenom" value="%{client.prenom}" key="profil.prenom" size="30"/></tr>
<tr><s:textfield  name ="client.nom" value="%{client.nom}" key="profil.nom" size="30"/></tr>
<tr><s:textfield  name ="client.login" value="%{client.login}" key="profil.login" size="30"/></tr>
<tr><s:textfield  name ="client.password" value="%{client.password}" key="profil.password" size="30"/></tr>
<tr><s:textfield  name ="client.email" value="%{client.email}" key="profil.email" size="30"/></tr>
<tr><s:textfield  name ="client.fax" value="%{client.fax}" key="profil.fax" size="30"/></tr>
<tr><s:textfield  name ="client.telephone" value="%{client.telephone}" key="profil.telephone" size="30"/></tr>
<tr><s:textfield  name ="client.titre" value="%{client.titre}" key="profil.titre" size="30"/></tr>

<tr><s:textfield  name ="adresse.codepostal" value="%{adresse.codepostal}" key="profil.cp" size="30"/></tr>
<tr><s:textfield  name ="adresse.departement" value="%{adresse.departement}" key="profil.dpt" size="30"/></tr>
<tr><s:textfield  name ="adresse.numero" value="%{adresse.numero}" key="profil.num" size="30"/></tr>
<tr><s:textfield  name ="adresse.pays" value="%{adresse.pays}" key="profil.pays" size="30"/></tr>
<tr><s:textfield  name ="adresse.rue" value="%{adresse.rue}" key="profil.rue" size="30"/></tr>
<tr><s:textfield  name ="adresse.ville" value="%{adresse.ville}" key="profil.ville" size="30"/></tr>

<s:submit key="profil.bouton"  align="center"/>

</table> 
</s:form>

</center>

