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

@WebServlet( name="Game", urlPatterns = "/games" )
public class GameEndpoint extends HttpServlet  {
	
	public static final String CONF_DAO_FACTORY = "daofactory";
	private GamesDAOImpl gamesDao;
	
	public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.gamesDao = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY )).getGamesDao();
    }
	
	public void doGet( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {	
		String token = Utilities.getCookieValue(request, "token");
		if(token != null && request.getParameter("type") == null) {
			ArrayList<Game> games = gamesDao.readGame(token.toString());
			String json = new Gson().toJson(games);
		    response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    
		    if(json.isEmpty())
		    {
		    	response.setStatus(204); // No content
		    }
		    else
		    {
		    	response.setStatus(200); // OK
		    }
		    
			request.setAttribute( "games", json );
			// Add username to request for frontend
			request.setAttribute("username", Utilities.getCookieValue(request, "username"));
			
			this.getServletContext().getRequestDispatcher( "/WEB-INF/pages/myGames.jsp" ).forward( request, response );
		}
		else if(request.getParameter("type").equals("leaderboard"))
		{
			ArrayList<Game> games = gamesDao.readBest();
			String json = new Gson().toJson(games);
			request.setAttribute( "leaderboard", json );
			
			response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.setStatus(200); // OK
			
		    // If username exists, put it in request for frontend
			String username = Utilities.getCookieValue(request, "username");
			if(username != null)
			{
				request.setAttribute("username", username);
			}
			
			this.getServletContext().getRequestDispatcher( "/WEB-INF/pages/leaderboard.jsp" ).forward( request, response );
		}
		else
		{
			this.getServletContext().getRequestDispatcher( "/WEB-INF/pages/notAuthorized" ).forward( request, response );
		}

	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {
		Game game = Utilities.getGameFromBody(request);

		gamesDao.create(game);
	    response.setStatus(201); // Created
	}
}
