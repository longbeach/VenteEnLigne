<%@ taglib prefix="s" uri="/struts-tags" %>
<link href="<s:url value="/css/styles.css"/>" rel="stylesheet" type="text/css"/>
<html  >
<s:head theme="xhtml"   /> 
<s:debug/>

<body>

<div align="right" >	
 <img src="<%=request.getContextPath()%>/images/utilisateur.gif" ><br>
 <s:text name="general.nom" /> : <s:property value="#session.leclient.nom" /><br>
 <s:text name="general.prenom" /> :  <s:property value="#session.leclient.prenom" />
</div>

<center>
<h1>Résultats de la recherche  </h1>

<s:if test="articles.size() != 0"> 

	<table class="table1">
		 <tr>
		  <th bgcolor="#79BCFF">Nom</th>
		  <th bgcolor="#79BCFF">Prix</th>
		  <th bgcolor="#79BCFF">Détails</th>
		 </tr>

	 <s:iterator value="articles" status="article">

		<tr >
		     <td bgcolor="#478D72" align="left">
		       <s:property value="nom" />
		     </td>
		 	 <td bgcolor="#478D72" align="left">
		       <s:property value="prix" />
		     </td>
		 	 <td bgcolor="#B9DBCC" align="left">
		       	<s:url id="visualiser" action="visualiser" > 
								<s:param name="numeroId" value="articleid"/> 
				</s:url>
				<s:a href="%{visualiser}" >Visualiser <img src="<%=request.getContextPath()%>/images/loupe.png"> </s:a> 				
		     </td>
	    </tr>	
		 </s:iterator>
	</table>

	<br />

    <s:url id="recherche" action="recherche" />	
     <s:a href="%{recherche}" >Faire une nouvelle recherche<br>
  	 <img src="<%=request.getContextPath()%>/images/recherche.gif">
  	 </s:a>  
 </s:if>
<s:else>
	<h2>Aucun article ne correspond à vos critères</h2>
	<br />
     <s:url id="recherche" action="recherche" />	
     <s:a href="%{recherche}" >Faire une nouvelle recherche<br>
  	 <img src="<%=request.getContextPath()%>/images/recherche.gif">
  	 </s:a>  
 </s:else>

</center>

</body>
</html>