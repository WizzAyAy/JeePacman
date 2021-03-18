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
	    
	    JSONObject info = new JSONObject();

	    JSONArray cosmeticsArray = new JSONArray();
	    JSONObject cosmetic1 = new JSONObject();
	    JSONObject cosmetic2 = new JSONObject();
	    JSONObject cosmetic3 = new JSONObject();

	    cosmetic1.put("price","10€");
	    cosmetic1.put("name","asiimov");
	    
	    cosmetic2.put("price","1000€");
	    cosmetic1.put("name","dragon Lord");
	    
	    cosmetic3.put("price","3000€");
	    cosmetic3.put("name","fire serpent");
	    
	    cosmeticsArray.add(cosmetic1);
	    cosmeticsArray.add(cosmetic2);
	    cosmeticsArray.add(cosmetic3);
	    
	    info.put("cosmetics", cosmeticsArray);
	    info.put("email", "jesuisriche@money.gold");
	    info.put("name", "WizzAy");
	    
	    
	    out.print("<table class=\"styled-table\">");
	    out.print("<thead>");
	    out.print("<tr> <th>Name</th> <th>Email</th> <th>Cosmetics</th> </tr>");
	    out.print("</thead>");
	    
	    out.print("<tbody>");
	    
      
        	out.print("<tr>");
	            out.print("<td>");
	            out.println(info.get("name"));
	            out.print("</td>");
	            
	            out.print("<td>");
	            out.println(info.get("email"));
	            out.print("</td>");
	            
	            
	            JSONArray cosmetics = (JSONArray) info.get("cosmetics");
	            Iterator<String> itCosmetics = cosmetics.iterator();
	            //affichade des players
	            int i = 0;
	            out.print("<td>");
	            while (itCosmetics.hasNext()) {
	            	JSONObject tmpCosmetic = (JSONObject) cosmetics.get(i);
	            	String price = (String) tmpCosmetic.get("price");
	            	String name = (String) tmpCosmetic.get("name");
	            	out.println(name + "(" + price + ")");
	            	
	            	itCosmetics.next();
	            	if(itCosmetics.hasNext()) out.println(" ,");
	         	    i++;
	            }
	            out.print("</td>");    
    	    out.print("</tr>");
        out.print("</tbody>");
	    out.print("</table>");
	    
	%>
	</div>
	
	
</body>
</html>