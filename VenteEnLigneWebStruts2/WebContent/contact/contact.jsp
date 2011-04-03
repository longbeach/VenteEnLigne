<%@ taglib prefix="s" uri="/struts-tags" %>
<link href="<s:url value="/css/styles.css"/>" rel="stylesheet" type="text/css"/>
<html  >
<s:head theme="ajax" title="Posez une question en ligne" />

<strong>Service client 24 heures sur 24.</strong>

Avez-vous des questions à poser avant de passer votre commande ? <br>
Vous pouvez contacter notre service client par email.
<s:form>
<table>
 		<tr>
			<s:textfield name="nomQuest"   label="Nom"  maxLength="50" size="20" />	
		</tr>
 		<tr>
			<s:textfield name="emailQuest"   label="Email"  maxLength="50" size="20" />	
		</tr>
 		<tr>
			<s:textfield name="telQuest"   label="Numéro de téléphone"  maxLength="50" size="20" />	
		</tr>
 		<tr>
			<s:textarea name="questionQuest"   label="Question"  maxLength="50" size="20" />	
		</tr>

</table>
</s:form>
</body>
</html>