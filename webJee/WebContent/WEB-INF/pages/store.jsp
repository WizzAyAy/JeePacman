<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>Pacman</title>
	<style type="text/css"><%@include file="../css/table.css" %></style>
	<style type="text/css">
	p{
		text-align: center;
	}
	</style>
</head>
<body>

<c:if test="${empty requestScope.username}">
		<%@ include file="../pages/notAuthorized.jsp" %>
</c:if>
<c:if test="${!empty requestScope.username}">

	<%@ include file="../components/header.jsp" %>	
		
	<div class="center">
	<%@ page import="org.json.simple.JSONObject"  %>
	<%@ page import="org.json.simple.parser.JSONParser"  %>
	<%@ page import="org.json.simple.JSONArray"  %>
	<%@ page import="java.util.Iterator"  %>
	
	<%		  
		String buy = (String) request.getAttribute("buy");
	    JSONParser parser = new JSONParser();
	    String attribut = (String) request.getAttribute("cosmetics");
	   
	    JSONArray cosmeticsArray = (JSONArray) parser.parse(attribut);

	    JSONObject cosmeticJson = new JSONObject();
	    
	    cosmeticJson.put("cosmetics", cosmeticsArray);
	    
   
	    out.print("<table class=\"styled-table\">");
	    
	    out.print("<thead>");
		if(cosmeticJson.toString().equals("{\"cosmetics\":[]}"))
			out.print("<tr> <th>VOUS AVEZ ACHETE TOUS LES COSMETICS OMG !!!</th> </tr>");
	    
		else 
			out.print("<tr> <th>Name</th> <th>Price</th> <th>Acheter</th> </tr>");
		
	    out.print("</thead>");
	    
	    out.print("<tbody>");
	    
      
	
       JSONArray cosmetics = (JSONArray) cosmeticJson.get("cosmetics");
       Iterator<String> itCosmetics = cosmetics.iterator();
       //affichade des players
       int i = 0;
       
		while (itCosmetics.hasNext()) {
			JSONObject tmpCosmetic = (JSONObject) cosmetics.get(i);
        	
			double price = (double) tmpCosmetic.get("price");
        	String name = (String) tmpCosmetic.get("name");
        	long id = (long) tmpCosmetic.get("id");
	    		       	
	       	out.print("<tr>");
		     	out.println("<td>" + name + "</td>");
		     	out.println("<td>" + price + " €</td>");
				out.println("<td><form method=\"POST\" action=\"/webJee/store\">");
				out.println("<input name=\"idCosmetic\" type=\"hidden\" value=" + id + ">");
				out.println("<input class=\"buy\" type=\"submit\" value=\"Buy me!!!\"></form></td>");
	       	out.print("</tr>");
	       	
	    	
	    	
	    	itCosmetics.next();
			i++;
        }  	
        out.print("</tbody>");
	    out.print("</table>");	    
	
	    %>

	</div>
	
	
	<%
	Object buyed = request.getAttribute("buy");
	if(buyed != null)
		out.print("<p>" + buyed.toString()  + "</p>");
	%>
</c:if>
</body>
</html>