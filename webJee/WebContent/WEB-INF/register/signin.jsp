<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SignIn</title>
</head>
<body>
<%@ include file="../components/headerUnconnect.jsp" %>
	<h1>Cr�ation du compte</h1>
	<form action="/webJee/signin" method="post">
		<table style="with: 50%">
		
		
		
			<tr><td>Username</td>
			<td><input type="text" name="username" /></td></tr>
			
			<tr><td>Email</td>
			<td><input type="text" name="email" /></td></tr>
			
			<tr><td>Mot de passe</td>
			<td><input type="password" name="password" /></td></tr>
			
		</table>
		
		<input type="submit" value="Submit" />
	</form>
</body>
</html>