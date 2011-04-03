<%@ taglib prefix="s" uri="/struts-tags" %>

<link href="<s:url value="/css/styles.css"/>" rel="stylesheet" type="text/css"/>
<s:head theme="xhtml"  />
<title><s:text name="inscription.titrePrincipal" /></title> 
<s:debug/>
<center>

<%@ include file="/inscription/etapes.jsp" %>
<br />

<h1><s:text name="inscription.etape3" /></h1>

<s:form action="confirmAdresse" method="POST" theme="xhtml"   >

 <table class="table1" >
    <tr><th colspan="2"><s:text name="inscription.titre2" /> </th></tr>
	<tr><s:textfield name="client.login" key="inscription.login" value="%{client.login}" required="true"/>	</tr>
	<tr><s:textfield name="client.password"  key="inscription.pwd"  value="%{client.password}" required="true"/>	</tr>
 </table>	

 <br/>

 <table class="table1" >
    <tr><th colspan="2"><s:text name="inscription.titre3" /></th></tr>
	<tr><s:textfield name="client.nom" key="inscription.nom" value="%{client.nom}" required="true"/>	</tr>
	<tr><s:textfield name="client.prenom" key="inscription.prenom" value="%{client.prenom}" required="true"/>	</tr>
	<tr><s:textfield name="client.telephone" key="inscription.telephone" value="%{client.telephone}" required="true"/>	</tr>
	<tr><s:textfield name="client.titre" key="inscription.titre4" value="%{client.titre}" required="true"/>	</tr>
	<tr><s:textfield name="client.fax" key="inscription.fax" value="%{client.fax}" required="true"/>	</tr>
	<tr><s:textfield name="client.email" key="inscription.email" value="%{client.email}" required="true"/>	</tr>
 </table>	

 <br/>

   <table class="table1">
	 <tr>
	   <td colspan="2">
	         <s:actionerror />
	   </td>
	</tr>
	<tr>
		<s:textfield name="adresse.codepostal"  key="inscription.cp" required="true"/>
		<s:textfield name="adresse.departement"  key="inscription.dpt" required="true"/>
		<s:textfield name="adresse.numero"  key="inscription.num" required="true"/>
		<s:textfield name="adresse.pays"  key="inscription.pays" required="true"/>
		<s:textfield name="adresse.rue"  key="inscription.rue" required="true"/>
		<s:textfield name="adresse.ville"  key="inscription.ville" required="true"/>

	</tr>
	</table>
       <br />
       <s:submit  name="inscription.bouton" align="center"/>
</s:form>

</center>