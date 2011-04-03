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
 				dojo.byId("prenom").value = "";
 				dojo.byId("nom").value = "";
 				dojo.byId("titre").value = "";
 				dojo.byId("email").value = ""; 			
 				dojo.byId("login").value = "";
 				dojo.byId("password").value = "";
 				dojo.byId("fax").value = "";
 				dojo.byId("telephone").value = "";

 				 // Champs du formulaire d'ajout 	 				
 				dojo.byId("prenomAjout").value = "";
 				dojo.byId("nomAjout").value = "";
 				dojo.byId("titreAjout").value = "";
 				dojo.byId("emailAjout").value = ""; 			
 				dojo.byId("loginAjout").value = "";
 				dojo.byId("passwordAjout").value = "";
 				dojo.byId("faxAjout").value = "";
 				dojo.byId("telephoneAjout").value = ""; 			
 			}
 		});			

 		dojo.event.topic.subscribe("/modif", function(data, type, request) {
 		    if(type == "before") {
 				var id = data.split("_")[1];
				var tr = dojo.byId("row_"+id);
 				var tds = tr.getElementsByTagName("td");
				dojo.byId("utilisateurid").value = id;
				
 				dojo.byId("prenom").value = dojo.string.trim(dojo.dom.textContent(tds[0]));
 				dojo.byId("nom").value = dojo.string.trim(dojo.dom.textContent(tds[1]));
 				dojo.byId("email").value = dojo.string.trim(dojo.dom.textContent(tds[2])); 			
 				dojo.byId("login").value = dojo.string.trim(dojo.dom.textContent(tds[3]));
 				dojo.byId("password").value = dojo.string.trim(dojo.dom.textContent(tds[4])); 			
 				dojo.byId("titre").value = dojo.string.trim(dojo.dom.textContent(tds[5]));
 				dojo.byId("fax").value = dojo.string.trim(dojo.dom.textContent(tds[6]));
 				dojo.byId("telephone").value = dojo.string.trim(dojo.dom.textContent(tds[7]));			
 			}
 		});
 	</script>
 </head>

 <body>
 <s:url action="list2" id="descrsUrl"/>
  
		<div style="width: 1400px;border-style: solid">
        	<div style="text-align: right;">
    			<sx:a notifyTopics="/rafraichir" >Rafraîchir la liste <img src="<%=request.getContextPath()%>/images/reload_page.png" ></sx:a>
    		</div>
    		   <sx:div id="utilisateurs" href="%{descrsUrl}" loadingText="Chargement en cours ..." listenTopics="/rafraichir"  
    		   showLoadingText="true" />      		       		   
        </div>
   <br/>
   <div style="width: 1400px;border-style: solid">
 		<p><font style="font-size: 1.5em;text-align: center;">
 		Modifier un client (Titre - Prenom - Nom - Email - Login - Password - Fax - Telephone)
 		</font></p>
			
		<s:form action="sauvegarder"  >	        
 		    <s:textfield id="utilisateurid" name="utilisateur.utilisateurid" cssStyle="display:none" /> 		    
 		    <s:textfield id="titre" label="Titre" name="utilisateur.titre" /> 
 			<s:textfield id="prenom" label="Prenom" name="utilisateur.prenom"/>
 			<s:textfield id="nom" label="Nom" name="utilisateur.nom"/>
 			<s:textfield id="email" label="Email" name="utilisateur.email"/>						 		
 			<s:textfield id="login" label="Login" name="utilisateur.login"/>
 			<s:textfield id="password" label="Password" name="utilisateur.password"/>
 			<s:textfield id="fax" label="Fax" name="utilisateur.fax"/>
 			<s:textfield id="telephone" label="Telephone" name="utilisateur.telephone"/> 		 		
 			<sx:submit  targets="utilisateurs" notifyTopics="/sauver" label="Modifier" />
 		</s:form>
 	</div>
 	
 	 <br/>
   <div style="width: 1400px;border-style: solid">
 		<p><font style="font-size: 1.5em;text-align: center;">
 		Ajouter un client (Titre - Prenom - Nom - Email - Login - Password - Fax - Telephone)
 		</font></p>
			
		<s:form action="sauvegarder" >			 
 		    <s:textfield id="titreAjout" label="Titre" name="utilisateur.titre"    />
 			<s:textfield id="prenomAjout" label="Prenom" name="utilisateur.prenom"    />
 			<s:textfield id="nomAjout" label="Nom" name="utilisateur.nom"/>
 			<s:textfield id="emailAjout" label="Email" name="utilisateur.email"/>				 		
 			<s:textfield id="loginAjout" label="Login" name="utilisateur.login"/>
 			<s:textfield id="passwordAjout" label="Password" name="utilisateur.password"/>
 			<s:textfield id="faxAjout" label="Fax" name="utilisateur.fax"/>
 			<s:textfield id="telephoneAjout" label="Telephone" name="utilisateur.telephone"/> 		 	
 			<sx:submit  targets="utilisateurs" notifyTopics="/sauver" label="Ajouter" />
 		</s:form>
 	</div>
 	 	
 	</center>
 </body>
</html>