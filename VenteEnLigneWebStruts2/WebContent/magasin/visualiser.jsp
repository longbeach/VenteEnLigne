<%@ taglib prefix="s" uri="/struts-tags" %>
<link href="<s:url value="/css/styles.css"/>" rel="stylesheet" type="text/css"/>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<s:head title="Détails de l'article" />
<body>
<center>
<div align="right">Nom: <s:property value="#session.leclient.nom" /> - Prenom:  <s:property value="#session.leclient.prenom" /></div>
<h1>Détails</h1>
 
<hr>

Voici le détail de l'article  <b><%= session.getAttribute("nomArt") %> :</b><br/>

<br/>
<s:form action ="ajouterArticle" >

<table  class="table1"  >
  <center>
	<tr align="center"><td height="35"> <b><s:label value="Article ID :"   /> </b></td> <td>   <s:property value="#session.monArticle.articleid" /> 	</td></tr>	
    <tr align="center"><td height="35">  <b><s:label value="Produit :"   /> </b>  </td> <td>    <s:property value="#session.monArticle.produitFk.nom" /> 	</td></tr>	
	<tr align="center"><td height="35">  <b><s:label value="Nom :"   />     </b> </td> <td>    <s:property value="#session.monArticle.nom" />  			</td></tr>	
	<tr align="center"><td height="35">  <b><s:label value="Prix :"   />   </b> </td> <td>     <s:property value="#session.monArticle.prix" />   		</td></tr>	
    <tr align="center"><td height="35">  <b><s:label value="Stock :"   />  </b> </td> <td>     <s:property value="#session.monArticle.stockFK.quantite" /> </td></tr>	
    <tr align="center"><td height="35">  <b><s:label value="Image :"   />  </b> </td> <td>    <img src="afficherImage.action?numeroId=<s:property value="#session.monArticle.articleid" />" />  </td></tr>
	<tr align="center"><td height="35">  <b><s:label value="Quantité souhaitée :"   /></b></td> <td>  <s:textfield name="quantite"  size="20"  value="1"   /> </td>	 </tr> 
  </center>
</table>

<br/>

<center><s:submit name="ajoutPanier" value="AJOUTER AU PANIER"  ></s:submit> </center>
</s:form> 

<s:url id="ficheJasper" action="afficheFichePDF" >
 <s:param name="numeroId"><s:property value="#session.monArticle.articleid" /></s:param>
</s:url>
<s:a href="%{ficheJasper}">Export de la fiche article au format PDF</s:a> 
<br />

<s:url id="affiche" action="afficheMagasin"/><s:a href="%{affiche}">Retour à la page d'affichage de la liste des articles.</s:a> 

</center>
</body>
</html>