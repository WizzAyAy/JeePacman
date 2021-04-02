package com.endpoints;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bean.Cosmetic;
import com.bean.User;
import com.dao.CosmeticsDAOImpl;
import com.dao.DAOFactory;
import com.dao.PlayerDAOImpl;
import com.dao.interfaces.CosmeticsDAO;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
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
		if(token != null && request.getParameter("info")==null) {		
			User player = playerDao.read(token);
			String json = new Gson().toJson(player);
		    response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.setStatus(200); // OK
		    
		    // Just adding username to the request to display it on the website
        	request.setAttribute("username", player.getUsername());
        	
			request.setAttribute( "user", json );
			this.getServletContext().getRequestDispatcher( "/WEB-INF/pages/myInfo.jsp" ).forward( request, response );
		}
		else if(token != null && request.getParameter("info").equals("cosmetics"))
		{
			CosmeticsDAOImpl cosDao = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY )).getCosmeticsDao();
			
			ArrayList<Cosmetic> cos = cosDao.readPlayerCosmetics(token);

			Gson gson = new Gson();
			JsonElement elmt = gson.toJsonTree(cos, new TypeToken<ArrayList<Cosmetic>>() {}.getType());
			
			if(!elmt.isJsonArray())
			{
				response.setStatus(404); // Didn't know which code to put there, it's 3 rn, TODO
			}
			else
			{
				JsonArray cosArray = elmt.getAsJsonArray();
				request.setAttribute("cosmetics", cosArray);
			}
			
			this.getServletContext().getRequestDispatcher( "/WEB-INF/pages/cosmetics.jsp" ).forward( request, response );
		}
		else
		{
			response.setStatus(401); // Unauthorized
			this.getServletContext().getRequestDispatcher( "/WEB-INF/pages/myInfo.jsp" ).forward( request, response );
		}
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
