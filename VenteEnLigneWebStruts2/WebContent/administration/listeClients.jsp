<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>

<center>
<p><font style="font-size: 2em;text-align: center;">Liste des clients</font></p>

<s:if test="utilisateurs.size > 0">
 <table border="1">
    <tr style="font-size: 1.5em;text-align: center;">
     <td>Prenom</td><td>Nom</td><td>Mail</td><td>Login</td><td>Password</td><td>Titre</td><td>Fax</td><td>Telephone</td><td>&nbsp;</td>
    </tr>
 	<s:iterator value="utilisateurs">
 		<tr id="row_<s:property value="utilisateurid"/>"> 			
 			<td><s:property value="prenom" /></td>
 			<td><s:property value="nom" /></td>
 			<td><s:property value="email" /></td> 	
 			<td><s:property value="login" /></td>
 			<td><s:property value="password" /></td> 		
 			<td><s:property value="titre" /></td>
 			<td><s:property value="fax" /></td>
 			<td><s:property value="telephone" /></td>
 			<td>
 				<s:url id="supprimerUrl" action="supprimer">
 					<s:param name="utilisateurid" value="utilisateurid" />
 				</s:url>
 				<sx:a href="%{supprimerUrl}" targets="utilisateurs" >Supprimer <img src="<%=request.getContextPath()%>/images/delete_user.png" ></sx:a>
 				<sx:a id="a_%{utilisateurid}"  notifyTopics="/modif">Modifier <img src="<%=request.getContextPath()%>/images/edit_user.png" ></sx:a>
 			</td>
 		</tr>
 	</s:iterator>
 </table>
</s:if>

</center>