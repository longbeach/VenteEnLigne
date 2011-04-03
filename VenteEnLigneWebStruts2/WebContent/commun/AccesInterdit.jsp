<%@ taglib prefix="s" uri="/struts-tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Accès interdit</title>
</head>
<body>
  <s:actionerror/>
<center>
	<hr>
	
		Vous n'avez pas les droits nécessaires pour accéder à cette fonctionnalité de l'application. 
		<br><br><img src="<%=request.getContextPath()%>/images/sens_interdit.gif"><br>
		<h4>Nom de l'exception:  </h4> <s:property value="exception" />
	
	<hr>
</center>
<!--<h3>Pile: </h3> <s:property value="%{exceptionStack}"/>-->
<!--<h4>Details Exception: </h4> <s:property value="exceptionStack" />-->

</body>
</html>