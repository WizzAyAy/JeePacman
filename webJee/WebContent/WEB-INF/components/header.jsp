<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<style type="text/css"><%@include file="../css/header.css" %></style>

<div class="header">
	<a href="/webJee" class="logo">Pacman</a>
  
    <c:if test="${empty requestScope.username}">
		<div class="header-right">
			<a href="/webJee/games?type=leaderboard">LeaderBoard</a>
		    <a href="/webJee/login" class="connect">Login</a>
		    <a href="/webJee/signin" class="connect">Sign In</a>
    	</div>
    </c:if>
    
    <c:if test="${!empty requestScope.username}">
		<div class="header-right">
			  <a href="/webJee/games">Mes parties</a>
			  <a href="/webJee/user">Mes infos</a>
			  <a href="/webJee/store">Store</a>
			  <a href="/webJee/games?type=leaderboard">LeaderBoard</a>
			  <a href="/webJee/coffee">Free coffee</a>
		      <a href="/webJee/logout" class="logout">Logout from <% out.println(request.getAttribute("username")); %></a>   
  		</div>
	</c:if>
	
</div>