<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>

<html>
<center>
 <head>
 	<sx:head parseContent="true" debug="false"/>
 	<script type="text/javascript">

 		dojo.event.topic.subscribe("/sauver", function(data, type, request) {
 		    if(type == "load") {

 		  		 // Champs du formulaire de modification 		  		
 				dojo.byId("utilisateurid").value = "";
 				dojo.byId("nom").value = "";
 				dojo.byId("prix").value = "";
 				dojo.byId("produitFk").value = "";
 				dojo.byId("stockFK").value = "";
 				dojo.byId("stockFKQuantite").value = ""; 				

 				 // Champs du formulaire d'ajout 	 				
 				dojo.byId("nomAjout").value = "";
 				dojo.byId("prixAjout").value = "";
 				dojo.byId("produitFkAjout").value = "";
 				dojo.byId("stockFKQuantiteAjout").value = ""; 						
 			}
 		});			

 		dojo.event.topic.subscribe("/modif", function(data, type, request) {
 		    if(type == "before") {
 				var id = data.split("_")[1];
				var tr = dojo.byId("row_"+id);
 				var tds = tr.getElementsByTagName("td");
				dojo.byId("articleid").value = id;
				
 				dojo.byId("nom").value = dojo.string.trim(dojo.dom.textContent(tds[0]));
 				dojo.byId("prix").value = dojo.string.trim(dojo.dom.textContent(tds[1]));
 				dojo.byId("produitFk").value = dojo.string.trim(dojo.dom.textContent(tds[2])); 	
 				dojo.byId("stockFK").value = dojo.string.trim(dojo.dom.textContent(tds[3]));			
 				dojo.byId("stockFKQuantite").value = dojo.string.trim(dojo.dom.textContent(tds[4]));
 					
 			}
 		});
 	</script>
 </head>

 <body>
 <s:url action="list2Articles" id="descrsUrl"/>
  
		<div style="width: 1400px;border-style: solid">
        	<div style="text-align: right;">
    			<sx:a notifyTopics="/rafraichir" >Rafraîchir la liste <img src="<%=request.getContextPath()%>/images/reload_page.png" ></sx:a>
    		</div>
    		<div style="text-align: right;">
    			<sx:a notifyTopics="/debutTimer" >Rafraîchir la liste toutes les 5 secondes<img src="<%=request.getContextPath()%>/images/reload_page.png" ></sx:a>
    		</div>
    		<div style="text-align: right;">
    			<sx:a notifyTopics="/stopTimer" >Arrêter le timer<img src="<%=request.getContextPath()%>/images/reload_page.png" ></sx:a>
    		</div>
    		<img id="rafraichissement" src="${pageContext.request.contextPath}/images/rechargement.gif" style="display:none"/>
    		
    		<sx:div id="articles" href="%{descrsUrl}" loadingText="Chargement en cours ..." listenTopics="/rafraichir"  
    		   showLoadingText="true" 
    		   updateFreq="5000" highlightColor="red" startTimerListenTopics="/debutTimer" stopTimerListenTopics="/stopTimer"  		       		   
    		   indicator="rafraichissement"
    		/>      		       		   
        </div>
   <br/>
   <div style="width: 1400px;border-style: solid">
 		<p><font style="font-size: 1.5em;text-align: center;">
 		Modifier un article (Nom - Prix - Produit ID - Stock ID - Quantite en stock)
 		</font></p>
			
		<s:form action="sauvegarderArticle"  >	        
 		    <s:textfield id="articleid" name="article.articleid" cssStyle="display:none" /> 		    
 		    <s:textfield id="nom" label="Nom" name="article.nom" /> 
 			<s:textfield id="prix" label="Prix" name="article.prix"/>
 			<s:textfield id="produitFk" label="Produit ID" name="article.produitFk.produitid"/>
 			<s:textfield id="stockFK" label="Stock ID" name="article.stockFK.stockid"/>
 			<s:textfield id="stockFKQuantite" label="Stock ID" name="article.stockFK.quantite"/>
 				 		
 			<sx:submit  targets="articles" notifyTopics="/sauver" label="Modifier" />
 		</s:form>
 	</div>
 	
 	 <br/>
   <div style="width: 1400px;border-style: solid">
 		<p><font style="font-size: 1.5em;text-align: center;">
 		Ajouter un article (Nom - Prix - Produit ID - Stock ID - Quantite en stock)
 		</font></p>
			
		<s:form action="sauvegarderArticle" >			 
 		    <s:textfield id="nomAjout" label="Nom" name="article.nom"    />
 			<s:textfield id="prixAjout" label="Prix" name="article.prix"    />
 			<s:textfield id="produitFkAjout" label="Produit ID" name="article.produitFk.produitid"    />
 			<s:textfield id="stockFKAjout" label="Stock ID" name="article.stockFK.stockid"    />
 			<s:textfield id="stockFKQuantiteAjout" label="Stock ID" name="article.stockFK.quantite"    />
 					 	
 			<sx:submit  targets="articles" notifyTopics="/sauver" label="Ajouter" />
 		</s:form>
 	</div>
 	 	
 	</center>
 </body>
</html>