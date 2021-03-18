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
	    JSONArray games = new JSONArray();
	    
	    JSONObject game1 = new JSONObject();
	    JSONObject game2 = new JSONObject();
	    
	    JSONArray players1 = new JSONArray();
	    JSONArray players2 = new JSONArray();
	    
	    JSONObject player1 = new JSONObject();
	    JSONObject player2 = new JSONObject();
	    
	    player1.put("username", "WizzAy");
	    player1.put("id", "10");
	    
	    player2.put("username", "bob");
	    player2.put("id", "1010");
	    
	    players1.add(player1);
	    
	    players2.add(player2);
	    players2.add(player1);
	    
	    
	    game1.put("players", players1);
	    game1.put("score", "1000");
	    game1.put("id", "game1");
	    
	    game2.put("players", players2);
	    game2.put("score", "20220");
	    game2.put("id", "game2");
	    
	    games.add(game1);
	    games.add(game2);
	    games.add(game2);
	    games.add(game2);
	    games.add(game2);
	    games.add(game2);
	    games.add(game2);
	    games.add(game2);
	    games.add(game2);
	    games.add(game2);
	    games.add(game2);
	    games.add(game2);
	    games.add(game2);
	  
	    

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
	            JSONArray players = (JSONArray) tmpGame.get("players");
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