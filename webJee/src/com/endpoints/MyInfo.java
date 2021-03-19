package com.endpoints;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bean.User;
import com.dao.DAOFactory;
import com.dao.PlayerDAOImpl;
import com.google.gson.Gson;

@WebServlet( name="MyInfo", urlPatterns = "/myinfo" )
public class MyInfo extends HttpServlet  {
	
	public static final String CONF_DAO_FACTORY = "daofactory";
	
	private PlayerDAOImpl playerDao;
	
	public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
        this.playerDao = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY )).getPlayerDao();
    }
	
	public void doGet( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {
		//this.getServletContext().getRequestDispatcher( "/WEB-INF/pages/myInfo.jsp" ).forward( request, response );
		
		//User player = playerDao.read(request.getAttribute("pseudo").toString());
		User player = playerDao.read("bob");
		String json = new Gson().toJson(player);
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.setStatus(200);
	    response.getWriter().write(json);
	}
}
