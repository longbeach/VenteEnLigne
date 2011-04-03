<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>

<center>
<p><font style="font-size: 2em;text-align: center;">Liste des articles</font></p>

<s:if test="articles.size > 0">
 <table border="1">
    <tr style="font-size: 1.5em;text-align: center;">
     <td>Nom</td><td>Prix</td><td>Produit ID</td><td>Stock ID</td><td>Quantite en stock</td><td>&nbsp;</td>
    </tr>
 	<s:iterator value="articles">
 		<tr id="row_<s:property value="articleid"/>" align="center"> 			
 			<td><s:property value="nom" /></td>
 			<td><s:property value="prix" /></td>
 			<td><s:property value="produitFk.produitid" /></td>
 			<td><s:property value="stockFK.stockid" /></td>
 			<td><s:property value="stockFK.quantite" /></td>
 			
 			<td>
 				<s:url id="supprimerArticleUrl" action="supprimerAdminArticle">
 					<s:param name="articleid" value="articleid" />
 				</s:url>
 				<sx:a href="%{supprimerArticleUrl}" targets="articles" >Supprimer <img src="<%=request.getContextPath()%>/images/editdelete.png" ></sx:a>
 				<sx:a id="a_%{articleid}"  notifyTopics="/modif">Modifier <img src="<%=request.getContextPath()%>/images/edit.png" ></sx:a>
 			</td>
 		</tr>
 	</s:iterator>
 </table>
</s:if>

</center>