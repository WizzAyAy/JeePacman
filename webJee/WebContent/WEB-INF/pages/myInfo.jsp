<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>Pacman</title>
	<style type="text/css"><%@include file="../css/table.css" %></style>
	</head>
<body>
<c:if test="${empty sessionScope.sessionUtilisateur}">
		<%@ include file="../pages/notAuthorize.jsp" %>
</c:if>
<c:if test="${!empty sessionScope.sessionUtilisateur}">
	<%@ include file="../components/header.jsp" %>	
	
	<div class="center">
	<%@ page import="org.json.simple.JSONObject"  %>
	<%@ page import="org.json.simple.parser.JSONParser"  %>
	<%@ page import="org.json.simple.JSONArray"  %>
	<%@ page import="java.util.Iterator"  %>
	
	<%
	
		JSONParser parser = new JSONParser();
	    String attribut = (String) request.getAttribute("myInfo");
	    JSONObject jsonObject = (JSONObject) parser.parse(attribut);
   			    
	    out.print("<table class=\"styled-table\">");
	    out.print("<thead>");
	    out.print("<tr> <th>Name</th> <th>Email</th> <th>Cosmetics</th> </tr>");
	    out.print("</thead>");
	    
	    out.print("<tbody>");
	    
      
        	out.print("<tr>");
	            out.print("<td>");
	            out.println(jsonObject.get("username"));
	            out.print("</td>");
	            
	            out.print("<td>");
	            out.println(jsonObject.get("email"));
	            out.print("</td>");
	            
	            
	            if(jsonObject.get("comestics") != null){
		            JSONArray cosmetics = (JSONArray) jsonObject.get("comestics");
		            Iterator<String> itCosmetics = cosmetics.iterator();
		            //affichade des players
		            int i = 0;
		            out.print("<td>");
		            out.print("<ul>");
		            while (itCosmetics.hasNext()) {
		            	JSONObject tmpCosmetic = (JSONObject) cosmetics.get(i);
		            	double price = (double) tmpCosmetic.get("price");
		            	String name = (String) tmpCosmetic.get("name");
		            	out.println("<li>" + name + " | " + price + " € </li>");
		            	
		            	itCosmetics.next();
		         	    i++;
		            }
		            out.print("</ul>");
		            out.print("</td>");   
	            }
	            else {
	            	out.print("<td>Aucun</td>");
	            }
    	    out.print("</tr>");
        out.print("</tbody>");
	    out.print("</table>");
	    
	   
	    
	%>
	</div>
	
</c:if>
</body>
</html>