<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<link href="<s:url value="/css/styles.css"/>" rel="stylesheet" type="text/css"/>

<sx:head parseContent="true"/>

<center>

<div align="right" >	
 <img src="<%=request.getContextPath()%>/images/utilisateur.gif" ><br>
 <s:text name="general.nom" /> : <s:property value="#session.leclient.nom" /><br>
 <s:text name="general.prenom" /> : <s:property value="#session.leclient.prenom" />
</div>

<h1><s:text name="paiement.titrePage" /></h1>

<table  class="table1"  >
  <tr>
   <td><s:text name="paiement.montantHT" />  : <s:property value="#session.montantTotal" /> <s:text name="paiement.euros" />  </td>
  </tr>
  <tr>
	<td><s:text name="paiement.montantTTC" /> : <s:property value="#session.montantTotal * 1.19" /> <s:text name="paiement.euros" />  </td>
  </tr>
  <tr>
   <td><s:text name="paiement.frais" /></td>
  </tr>
  <tr>
   <td><s:text name="paiement.regler" /> : <s:property value="(#session.montantTotal * 1.19) + 4.50" /> <s:text name="paiement.euros" />  </td>
  </tr>
</table>

<br /><img src="<%=request.getContextPath()%>/images/cartecredit.gif"><br />

<s:text name="paiement.phrase1" /><br/>
<s:form	action="payer" method="post"  theme="xhtml" tooltipConfig="#{'tooltipIcon':'/images/info.png'}" >

<s:actionerror />
(Type CC / N° CC pour test : Visa / 4716718496946025)

<table  class="table1"  >
        <tr>
 			<s:select key="paiement.typecc"
        		name="typeCC"
        		headerKey="-1" headerValue="Selectionnez un type"
        		list="#{'VISA':'Visa', 'MASTERCARD':'Eurocard / MasterCard', 'AMEX':'American Express','CB':'Carte Bancaire', 'CA':'Carte Aurore'}"
        		value="typeccSelectionne"        		
        		required="true"
 			/>
        </tr>
        <tr>
			<s:textfield name="numCC" key="paiement.numcc"  maxLength="50" size="20" required="true" />  
  		</tr>
        <tr>
			<s:textfield name="nomTitulaire"   key="paiement.nomcc" maxLength="50" size="20" required="true"/>	
		</tr>
		<tr>
			<s:textfield name="codeSecurite"   key="paiement.codesecu"  tooltip="%{getText('paiement.tooltip')}" required="true"/>
		</tr> 		      
 		<tr>
           <td><s:text name="paiement.dateexpi" />:</td>
           <td>
				<sx:datetimepicker name="expirationDate" key="paiement.dateexpiMois"   displayFormat="MM/yyyy" required="true"/> 								
			</td>			
		</tr>

<s:submit  key="paiement.bouton" align="center"/> 	<s:reset  value="Reset" align="center"/>
</table>
</s:form>

<br>

</center>