<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<%@ taglib prefix="s" uri="/struts-tags" %>
<s:head theme="simple" />
<title><s:text name="inscription.titrePrincipal" /></title> 
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<body>
<center>
	<h1><s:text name="inscription.success" /> !</h1>
<br/>

	<img src="<%=request.getContextPath()%>/images/inscriptionReussie.png">
	<br/>	<br/>
    <s:label key="getText('inscription.nom')" /> : <s:property value="client.nom"   />
	<br/>
	<s:label key="getText('inscription.prenom')" /> : <s:property value="client.prenom"   />

	<br/>	<br/>
    <a href="<%=request.getContextPath()%>/index.jsp"><s:text name="inscription.retour" /></a> 

</center>
</body>
</html>