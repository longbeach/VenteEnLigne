<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Clients EJB / Web</title>
</head>
<body>

<h2>Module Web utilisé en tant que client EJB</h2>  
  <a href="TestServlet">Cliquez ici pour appeler le session bean via la servlet TestServlet</a> 

<br>

<h2>TestTransactionBMT</h2>  
  <a href="TestTransactionBMT">Cliquez ici pour appeler la servlet TestTransactionBMT</a> 

<br>

<h2>Invocation de Web Service</h2>  
  <a href="TestServiceServlet">Cliquez ici pour appeler le web service de verification de n° de carte de crédit.</a> 
  
<br>
  
<h2>Invocation de TestTimer Service  (démarrage)</h2>  
<a href="TestTimer?appel=demarrer">Cliquez ici pour appeler le timer TimerStocks</a> 
  
<br>
  
<h2>Invocation de TestTimer Service (arrêt)</h2>  
<a href="TestTimer?appel=stopper">Cliquez ici pour arrêter le timer TimerStocks</a>   
  
<br><br>

<h2>Affichage d'une image</h2>
<a href="DisplayBlobExample">Cliquez ici pour afficher une image</a>

<br><br>

<h2>Invocation de ClientCompteurServlet</h2>
<a href="ClientCompteurServlet">Cliquez ici pour appeler le compteur</a>

<br><br>
  
</body>
</html>