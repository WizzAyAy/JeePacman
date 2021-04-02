package com.endpoints;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bean.User;
import com.dao.DAOFactory;
import com.dao.PlayerDAOImpl;
import com.google.gson.Gson;
import com.mysql.cj.Session;

@WebServlet( name="User", urlPatterns = "/user" )
public class UserEndpoint extends HttpServlet  {
	
	public static final String CONF_DAO_FACTORY = "daofactory";
	private PlayerDAOImpl playerDao;
	
	public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.playerDao = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY )).getPlayerDao();
    }
	
	public void doGet( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {
		
		String token = Utilities.getCookieValue(request, "token");
		if(token != null) {		
			User player = playerDao.read(token);
			String json = new Gson().toJson(player);
		    response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.setStatus(200); // OK
		    
		    // Just adding username to the request to display it on the website
        	request.setAttribute("username", player.getUsername());
        	
			request.setAttribute( "user", json );
		}
		else
		{
			response.setStatus(401); // Unauthorized
		}
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/pages/myInfo.jsp" ).forward( request, response );
	}
	
	public void doPut(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException 
	{
		User user = Utilities.getUserFromBody(request);
		
		String token = Utilities.getCookieValue(request, "token");
		if(token != null) {
			this.playerDao.update(user);
		    response.setStatus(201); // Created
		   
		    // Just adding username to the request to display it on the website
        	request.setAttribute("username", user.getUsername());
		}
		else
		{
			response.setStatus(401); // Unauthorized
		}
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/pages/myInfo.jsp" ).forward( request, response );
	}
	
	public void doDelete(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException 
	{
		User user = Utilities.getUserFromBody(request);
		
		String token = Utilities.getCookieValue(request, "token");
		if(token != null) {
			this.playerDao.delete(user);
		    response.setStatus(204); // No content
		}
		else
		{
			response.setStatus(401); // Unauthorized
		}
		
		this.getServletContext().getRequestDispatcher( "/Logout" ).forward( request, response );
	}

}
