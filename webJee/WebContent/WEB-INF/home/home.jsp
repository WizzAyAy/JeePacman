<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Pacman</title>
</head>
<body>
	<c:if test="${!empty sessionScope.sessionUtilisateur}">
		<%@ include file="../components/headerUnconnect.jsp" %>
	</c:if>
	
	<c:if test="${empty sessionScope.sessionUtilisateur}">
		<%@ include file="../components/hearderConnect.jsp" %>
	</c:if>
	
<p>ici une barre pour rechercher les parties d'un joueur ou une partie acec son id</p>
<p>les 20 derniers parties ici</p>

</body>
</html>