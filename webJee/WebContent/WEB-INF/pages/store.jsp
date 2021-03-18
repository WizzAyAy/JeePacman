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
	<%@ include file="../components/header.jsp" %>	
		
	<div class="center">
	<%@ page import="org.json.simple.JSONObject"  %>
	<%@ page import="org.json.simple.parser.JSONParser"  %>
	<%@ page import="org.json.simple.JSONArray"  %>
	<%@ page import="java.util.Iterator"  %>
	
	<%		    
	    
	    JSONObject cosmeticJson = new JSONObject();

	    JSONArray cosmeticsArray = new JSONArray();
	    JSONObject cosmetic1 = new JSONObject();
	    JSONObject cosmetic2 = new JSONObject();
	    JSONObject cosmetic3 = new JSONObject();
	    JSONObject cosmetic4 = new JSONObject();
	    JSONObject cosmetic5 = new JSONObject();

	    cosmetic1.put("price","10€");
	    cosmetic1.put("name","asiimov");
	    
	    cosmetic2.put("price","1000€");
	    cosmetic2.put("name","dragon Lord");
	    
	    cosmetic3.put("price","3000€");
	    cosmetic3.put("name","fire serpent");
	    
	    cosmetic4.put("price","1€");
	    cosmetic4.put("name","safari Mesh");
	    
	    cosmetic5.put("price","500€");
	    cosmetic5.put("name","howl");
	    
	    cosmeticsArray.add(cosmetic1);
	    cosmeticsArray.add(cosmetic2);
	    cosmeticsArray.add(cosmetic3);
	    cosmeticsArray.add(cosmetic4);
	    cosmeticsArray.add(cosmetic5);
	    
	   
	    
	    cosmeticJson.put("cosmetics", cosmeticsArray);
	   
	    out.print("<table class=\"styled-table\">");
	    out.print("<thead>");
	    out.print("<tr> <th>Name</th> <th>Price</th> </tr>");
	    out.print("</thead>");
	    
	    out.print("<tbody>");
	    
      
	
       JSONArray cosmetics = (JSONArray) cosmeticJson.get("cosmetics");
       Iterator<String> itCosmetics = cosmetics.iterator();
       //affichade des players
       int i = 0;
       
		while (itCosmetics.hasNext()) {
	    	out.print("<tr>");
	       	JSONObject tmpCosmetic = (JSONObject) cosmetics.get(i);
	       	String price = (String) tmpCosmetic.get("price");
	       	String name = (String) tmpCosmetic.get("name");
		       	out.println("<td>" + name + "</td>");
		       	out.println("<td>" + price + "</td>");
	       	out.print("</tr>");
	       	itCosmetics.next();
	    	i++;
	    	
        }    	
        out.print("</tbody>");
	    out.print("</table>");
	    
	%>
	</div>
	
</body>
</html>