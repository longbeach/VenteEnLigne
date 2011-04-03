<%@ taglib prefix="s" uri="/struts-tags" %>
<link href="<s:url value="/css/styles.css"/>" rel="stylesheet" type="text/css"/>
<html>
<body>

<s:debug/>

<div align="right" > <img src="<%=request.getContextPath()%>/images/utilisateur.gif" ><br>Nom: <s:property value="#session.leclient.nom" /><br>Prenom:  <s:property value="#session.leclient.prenom" /></div>

<center>
<h1>Votre panier</h1>

<s:if test="#session.montantTotal == 0">
	<hr>
	<h3>Le panier est vide</h3>
 	<img src="<%=request.getContextPath()%>/images/panierVide.jpg" >
	<hr>
	<br>
	<s:url id="affiche" action="afficheMagasin"/> <s:a href="%{affiche}">Ajouter un article</s:a> 
</s:if>
<s:else>

<br>
<!--  Lister les articles présents dans la ArrayList (en session) -->

	<table BORDER=2 CELLPADDING=4 width="1000">
	<th  bgcolor="#3333FF" colspan="7"><font color="#FFFFFF">Liste des articles dans le panier</font></th>
	    <tr align="center" BGCOLOR="#E9FC01">
			<td>&nbsp</td>
			<td>Article ID</td>
			<td>Nom</td>
			<td>Prix Unitaire</td>
			<td>Quantité</td>
			<td>Prix total</td>
			<td>&nbsp</td>
		</tr>
		<s:iterator  value="#session.mesArticles" status="ArticlePanier">
		<tr align="center" BGCOLOR="#E9FC01" >
				<td BGCOLOR="#E9FC01">&nbsp</td>
				<td>
					<s:property value ="article.articleid"/>
				</td>
				<td>
					<s:property value ="article.nom"/>
				</td>
				<td> <!-- Prix unitaire -->
					<s:property value ="article.prix"/>
				</td>
				<td> <!-- Quantite -->
					<s:textfield name="qtite" value="%{quantite}"> </s:textfield>
				</td>
				<td> <!-- Prix total -->
										
                    <s:text name="article.prix * quantite" />		

				</td>
				<td >
					<s:url id="supprimer" action="supprimerArticle" > 
						<s:param name="articleid" value="article.articleid"/> 
					</s:url>
						<s:a href="%{supprimer}">Supprimer</s:a> 
				</td>
		</tr>
		</s:iterator>
        <tr align="center" BGCOLOR="#E9FC01">
			<td>Montant total</td>
			<td>&nbsp</td>
			<td>&nbsp</td>
			<td>&nbsp</td>
			<td>
				<s:url id="recalculer" action="recalculerTotal" > 
						<s:param name="articleid" value="article.articleid"/> 
					</s:url>
				<s:a href="%{recalculer}" >Recalculer</s:a> 
			</td>
			<td><s:property value="#session.montantTotal" /></td>
			<td>&nbsp</td>
		</tr>
	</table> 
<br>
La taxe et les frais de livraison seront ajoutés quand vous passez la commande.
<br><br>
<s:url id="affiche" action="afficheMagasin"/> <s:a href="%{affiche}">Ajouter un autre article </s:a> 
 -
<s:url id="vider" action="viderPanier"/> <s:a href="%{vider}"> Vider le panier </s:a> 
- 
<s:url id="commander" action="commander"/> <s:a href="%{commander}">Passez votre commande maintenant</s:a> 

</s:else>

</center>
</body>
</html>
