<%@ taglib prefix="s" uri="/struts-tags" %>


<link href="<s:url value="/css/styles.css"/>" rel="stylesheet" type="text/css"/>
<s:head theme="xhtml"   /> 
<s:debug  />
<center>

<h2><s:text name="login.titre" /></h2>

<s:form action="doLogin" method="POST" theme="xhtml"   >
   <table class="table1">
	<tr>
		<td colspan="2"> <s:text name="login.phrase"></s:text>  </td>
	</tr>
	
	 <tr>
	   <td colspan="2">
	         <s:actionerror />
	   </td>
	</tr>
	<s:textfield name="login"  key="login.login" />
	<s:password name="password" key="login.pwd" />
	<s:submit key="login.bouton" align="center"/>
    </table>
</s:form>
<br/>

	<br/>	<br/>
    <s:url id="url"	value="/index.jsp" />
	<s:a href="%{url}" theme="xhtml">
		 <s:text name="login.retour" />
	</s:a>

</center>