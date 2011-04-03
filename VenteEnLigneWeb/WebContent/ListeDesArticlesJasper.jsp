<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Interface de reporting</title>

</head>
<body bgcolor="#FFFFB9">

<center>
<form action="ListeArticlesJasperServlet">
<h1>Bienvenue sur l'interface de reporting </h1>

<br></br>
Sélectionnez le nom du catalogue : 
		<select name="leCatalogue">
			<option value="Sport">Sport</option>
			<option value="Gateaux">Gateaux</option>
			<option value="Informatique">Informatique</option>
			<option value="Bricolage">Bricolage</option>
		</select>
		
<br></br>		
Sélectionnez le format du rapport : 		
		<select name="typeRapport">
			<option value="pageHTML">HTML</option>
			<option value="pdf">PDF</option>
			<option value="xls">Excel</option>
			<option value="rtf">RTF</option>
			<option value="word2007">Word 2007</option>
		</select>
<br></br>
<input type="submit" value="Générer le rapport" />

</form>
</center>
</body>
</html>
