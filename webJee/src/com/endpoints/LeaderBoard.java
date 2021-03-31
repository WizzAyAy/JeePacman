package com.endpoints;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bean.Game;
import com.dao.DAOFactory;
import com.dao.GamesDAOImpl;
import com.google.gson.Gson;

@WebServlet( name="LeaderBoard", urlPatterns = "/leaderboard" )
public class LeaderBoard extends HttpServlet  {
	
	public static final String CONF_DAO_FACTORY = "daofactory";
	private GamesDAOImpl gamesDao;
	
	public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.gamesDao = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY )).getGamesDao();
    }
	
	public void doGet( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {
		HttpSession session = request.getSession();
		ArrayList<Game> games = gamesDao.readBest();

		String json = new Gson().toJson(games);		
			
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.setStatus(200);
		request.setAttribute( "LeaderBoard", json );
		this.getServletContext().getRequestDispatcher( "/WEB-INF/pages/leaderboard.jsp" ).forward( request, response );

	}
}
