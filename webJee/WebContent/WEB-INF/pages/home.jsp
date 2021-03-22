<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>Pacman</title>
	<style type="text/css"><%@include file="../css/home.css" %></style>
	<link rel="icon" type="image/png" href="../assets/favicon.ico" />
</head>
<body>

	<%@ include file="../components/header.jsp" %>
	<div class="center">
	<table>
	
		<tr><td><h1 style="text-align:center">Bonjour jeune pandawan !</h1></td></tr>
		<tr><td><h2 style="text-align:center">bienvenue sur Pacman, sur ce site vous allez pouvoir</h2></td></tr>
		<tr><td>
				<ul>
					<li>Consulter vos anciennes parties | <i>Mes parties</i></li>
					<li>Consulter vos informations de compte | <i>Mes infos</i></li>
					<li>Vous équipez de skin vraiment beau,
					dans le but d'avoir un max de succes avec la gente feminine | <i>Store</i></li>
					<li>Admirez les meilleurs scores des joueurs du monde entier | <i>LeaderBoard</i></li>
				</ul>
		</td></tr>
				
	</table>
	</div>
	
	<%
	out.println(session.getAttribute("username"));
	out.println(session.getAttribute("token"));
	%>
	
	
</body>
</html>