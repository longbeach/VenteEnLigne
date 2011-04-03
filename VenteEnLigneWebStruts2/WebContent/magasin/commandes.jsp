<%@ taglib prefix="s" uri="/struts-tags" %>
<link href="<s:url value="/css/styles.css"/>" rel="stylesheet" type="text/css"/>

<html>
<body>
<center>

<div align="right" >	
 <img src="<%=request.getContextPath()%>/images/utilisateur.gif" ><br>
 <s:text name="general.nom" /> : <s:property value="#session.leclient.nom" /><br>
 <s:text name="general.prenom" /> :  <s:property value="#session.leclient.prenom" />
</div>

<h1>Liste des commandes</h1>

<table class="table1">
<tr>
  <td >
	<table>
	<th bgcolor="#3333FF"><font color="#FFFFFF">Commande ID</font></th>
		<s:iterator  value="commandeId" >
			<tr bgcolor="#478D72" >
				<td height="35">
					<font color="#FFFFFF"><s:property /></font>
				</td>
			</tr>
		</s:iterator>
	</table> 
 </td>
 <td  >
	<table>
	<th  bgcolor="#3333FF"><font color="#FFFFFF">Article</font></th>
		<s:iterator  value="articleNom" >
			<tr bgcolor="#478D72">
				<td height="35">
					<font color="#FFFFFF"><s:property /></font>
				</td>
			</tr>
		</s:iterator>
	</table> 
 </td>
 <td  >
	<table>
	<th  bgcolor="#3333FF"><font color="#FFFFFF">Date</font></th>
		<s:iterator  value="dateCommande" >
			<tr bgcolor="#478D72">
				<td height="35">
					<font color="#FFFFFF"><s:property /></font>
				</td>
			</tr>
		</s:iterator>
	</table> 
  </td>
<tr>
</table>

<br/>

<s:url id="afficheC" action="afficherPanier"/><s:a href="%{afficheC}"><img src="<%= request.getContextPath() %>/images/caddie.gif"> <br>Voir mon panier</s:a>  

</center>
</body>
</html>