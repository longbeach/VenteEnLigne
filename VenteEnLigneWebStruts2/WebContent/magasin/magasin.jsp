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

<h1><s:text name="magasin.titre" /></h1>

<table class="table1" >
<tr>
  <td>
	<table>
	<th bgcolor="#3333FF"><font color="#FFFFFF"><s:text name="magasin.catalogue" /></font></th>
		<s:iterator  value="catalNom" >
			<tr bgcolor="#478D72">
				<td height="35">
					<font color="#FFFFFF"><s:property /></font>
				</td>
			</tr>
		</s:iterator>
	</table> 
 </td>
 <td>
	<table>
	<th  bgcolor="#3333FF"><font color="#FFFFFF"><s:text name="magasin.produit" /></font></th>
		<s:iterator  value="prodNom" >
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
	<th bgcolor="#3333FF"><font color="#FFFFFF"><s:text name="magasin.article" /></font></th>
		<s:iterator  value="artNom" >
			<tr bgcolor="#478D72">
				<td height="35">
					<font color="#FFFFFF"><s:property /></font>
				</td>
			</tr>
		</s:iterator>
	</table> 
  </td>
  <td>
	<table>
	<th bgcolor="#3333FF"><font color="#FFFFFF"><s:text name="magasin.prix" /></font></th>
		<s:iterator  value="articlePrix" >
			<tr bgcolor="#478D72">
				<td height="35">
					<font color="#FFFFFF"><s:property /></font>
				</td>
			</tr>
		</s:iterator>
	</table> 
  </td>
  <td>
	<table>
	<th bgcolor="#3333FF"><font color="#FFFFFF"><s:text name="magasin.stock" /></font></th>
		<s:iterator  value="stockQtite" >
			<tr bgcolor="#478D72">
				<td height="35">
					<font color="#FFFFFF"><s:property /></font>
				</td>
			</tr>
		</s:iterator>
	</table> 
  </td>


  <td>
	<table>
	<th bgcolor="#3333FF"><font color="#FFFFFF"><s:text name="magasin.details" /></font></th>
		<s:iterator  value="articleNumero" >
			<tr bgcolor="#B9DBCC">
				<td height="35">
					<s:url id="visualiser" action="visualiser" > 
						<s:param name="numeroId" value="%{top}"/> 
					</s:url>
					<s:a href="%{visualiser}" ><s:text name="magasin.visualiser" /> <img src="<%=request.getContextPath()%>/images/loupe.png"> </s:a> 
				</td>
			</tr>
		</s:iterator>
	</table> 
  </td>
<tr>
</table>

<br/>

<s:url id="afficheC" action="afficherPanier"/><s:a href="%{afficheC}"><img src="<%= request.getContextPath() %>/images/caddie.gif"> <br><s:text name="magasin.lien" /></s:a>  

</center>
</body>
</html>