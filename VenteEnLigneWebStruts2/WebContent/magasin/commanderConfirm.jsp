<%@ taglib prefix="s" uri="/struts-tags" %>
<link href="<s:url value="/css/styles.css"/>" rel="stylesheet" type="text/css"/>

<html>
<body>
<center>
<div align="right" >	 <img src="<%=request.getContextPath()%>/images/utilisateur.gif" ><br>Nom: <s:property value="#session.leclient.nom" /><br>Prenom:  <s:property value="#session.leclient.prenom" /></div>
<h1>Paiement accepté</h1>

<br>
Votre commande est en cours de traitement pour livraison. Un mail de confirmation vous a été envoyé. (MDB)<br>
Cliquez ici pour exporter votre bon de commande au format PDF. (JasperReports)

Format de la facture JasperReports :
<pre>
Nom Prenom  DateduJour
nomArticle 1   Prix
nomArticle 2   Prix 
					Total : 445 euros

Numero de CC xxxxxxxxxxxxxxxxxxx4353

Adresse de facturation :
Ville
Rue
Numero
</pre>

<br><br>

</center>
</body>
</html>
