<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<link href="<s:url value="/css/styles.css"/>" rel="stylesheet" type="text/css"/>

<!--  En cas de fin de session, l'utilisateur est automatiquement redirigé vers la page principale -->
<meta http-equiv="refresh" content="<%= session.getMaxInactiveInterval() %>;url=index.jsp"   >

<div style="width: 100%; height: 100%; background-color: #ABBAF3" >
<strong><s:text name="header.titre" /></strong>
</div>  