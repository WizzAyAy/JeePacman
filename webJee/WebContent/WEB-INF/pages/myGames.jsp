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
	
		JSONParser parser = new JSONParser();
	    String attribut = (String) request.getAttribute("myGames");
	    JSONArray games = (JSONArray) parser.parse(attribut);
	    
	    out.print("<table class=\"styled-table\">");
	    out.print("<thead>");
	    out.print("<tr> <th>Score</th> <th>Player</th> </tr>");
	    out.print("</thead>");
	    
	    out.print("<tbody>");
	    //on boucles sur toutes les games
        Iterator<String> itGames = games.iterator();
        int i = 0;
        while (itGames.hasNext()) {
        	JSONObject tmpGame = (JSONObject) games.get(i);
        	
        	out.print("<tr>");
	            out.print("<td>");
	            out.println(tmpGame.get("score"));
	            out.print("</td>");
	            
	            //recuperation des joueurs de la game i
	            JSONArray players = (JSONArray) tmpGame.get("users");
	            Iterator<String> itplayers = players.iterator();
	            //affichade des players
	            int j = 0;
	            out.print("<td>");
	            while (itplayers.hasNext()) {
	            	//recuperation du joueur i dans les joeurs
	            	JSONObject tmpPlayer = (JSONObject) players.get(j);
	            	String username = (String) tmpPlayer.get("username");
	            	out.println(username);
	            	
	            	itplayers.next();
	            	if(itplayers.hasNext()) out.println(" ,");
	         	    j++;
	            }
	            out.print("</td>");
	            
	    	 
    	    out.print("</tr>");
    	    
    	    itGames.next();
    	    i++;
        }
        out.print("</tbody>");
	    out.print("</table>");
	    
	%>
	</div>
	
</body>
</html>