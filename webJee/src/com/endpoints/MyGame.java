package com.endpoints;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.bean.Game;
import com.bean.User;
import com.dao.DAOFactory;
import com.dao.GamesDAOImpl;
import com.google.gson.Gson;

@WebServlet( name="MyGame", urlPatterns = "/mygame" )
public class MyGame extends HttpServlet  {
	
	public static final String CONF_DAO_FACTORY = "daofactory";
	private GamesDAOImpl gamesDao;
	
	public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.gamesDao = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY )).getGamesDao();
    }
	
	public void doGet( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {
		
		
		HttpSession session = request.getSession();
		Object token = session.getAttribute("token");
		if(token != null) {
			ArrayList<Game> games = gamesDao.readGame(token.toString());
			String json = new Gson().toJson(games);
		    response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.setStatus(200);
			request.setAttribute( "myGames", json );
		}
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/pages/myGames.jsp" ).forward( request, response );
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {
		String body = Utilities.getBody(request);
    	System.out.println(body);
    	
    	JSONParser parser = new JSONParser();
	    JSONObject ids = null;
		try {
			ids = (JSONObject) parser.parse(body);
			 
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(ids);
		
		Game game = new Game();
		game.setScore(Integer.parseInt(ids.get("score").toString()));
		
		JSONArray users = (JSONArray) ids.get("users");
		
		Iterator<String> itUser = users.iterator();
        int i = 0;
        
        while (itUser.hasNext()) {
        	User u = new User();
        	JSONObject user = (JSONObject) users.get(i);
        	
        	System.out.println(user);
        	u.setUsername(user.get("pseudo").toString());
        	game.addUser(u);
        	
        	itUser.next();
     	    i++;
        }
		
		gamesDao.create(game);
	   
	}
}
