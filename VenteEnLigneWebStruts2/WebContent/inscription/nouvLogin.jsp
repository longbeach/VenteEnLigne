<%@ taglib prefix="s" uri="/struts-tags" %>
<link href="<s:url value="/css/styles.css"/>" rel="stylesheet" type="text/css"/>
<s:head theme="xhtml"   /> 
<title><s:text name="inscription.titrePrincipal" /></title>

<s:debug/>
<center>

<%@ include file="/inscription/etapes.jsp" %>
<br />

<h1><s:text name="inscription.etape1" /></h1>

<s:form action="nouvLogin" method="POST" theme="xhtml" >
   <table class="table1">
	<tr>
		<td colspan="2"> 
			<s:text name="inscription.titre" /> 
        </td>
	</tr>	
	<tr>
	   <td colspan="2">
	         <s:actionerror />
	   </td>
	</tr>

	<s:textfield name="client.login" key="inscription.login" required="true"/>
	<s:password name="client.password" key="inscription.pwd" required="true"/>
   </table>
<br/>
	<s:submit name="inscription.bouton" align="center"/>
</s:form>
<br/><br/><br/>
    <a href="<%=request.getContextPath()%>/index.jsp"><s:text name="inscription.retour" /></a>
</center>