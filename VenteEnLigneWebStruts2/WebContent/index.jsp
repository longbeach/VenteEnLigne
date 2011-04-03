<%@ taglib prefix="s" uri="/struts-tags"%>

<s:head theme="xhtml"   /> 
<title><s:text name="index.titre" /></title>
<link rel="stylesheet" type="text/css"
  href="<%= request.getContextPath() + "/css/styles.css" %>">

<body>
<br><br><br><br>
<center>

<br /><br />
<img src="<%=request.getContextPath()%>/images/login_icon.jpg"> <img src="<%=request.getContextPath()%>/images/inscription_icon.png">
<br /><br /> 
<a href="activerLogin.action"><s:text name="index.connexion" /> </a>  |  <a href="inscription.action"><s:text name="index.inscription" /> </a>

<br /><br /> <br /><br /><br /><br />
<s:text name="index.phrase1" />

<br/><br/>

<s:url id="url"	action="changerLangue.action">
	<s:param name="request_locale">fr</s:param>
</s:url>
<s:a href="%{url}" theme="xhtml">
	<img src="<%=request.getContextPath()%>/images/FR_drapeau.gif" width="23" height="15" border="0" title="<s:text name="index.langFR"/>">  
</s:a> 
&nbsp;
<s:url id="url"	action="changerLangue.action">
	<s:param name="request_locale">en</s:param>
</s:url>
<s:a href="%{url}" theme="xhtml">
	<img src="<%=request.getContextPath()%>/images/EN_drapeau.gif" width="23" height="15" border="0" title="<s:text name="index.langEN"/>">  
</s:a> 

</center>
