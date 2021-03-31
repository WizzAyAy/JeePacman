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
import com.google.gson.Gson;

@WebServlet( name="Store", urlPatterns = "/store" )
public class Store extends HttpServlet  {
	
	public static final String CONF_DAO_FACTORY = "daofactory";
	private CosmeticsDAOImpl cosmeticDao;
	
	
	public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO cosmetics */
        this.cosmeticDao = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY )).getCosmeticsDao();
    }
	
	public void doGet( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {
		/* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();
        
        Object token = session.getAttribute("token");
        /* 	FAIRE LA REQUETE POUR RECUP TOUS LES COSMETICS SAUF CEUX DE LA TABLE PLAYERS*/
        if(token != null) {
	        ArrayList<Cosmetic> cosmetics = cosmeticDao.readPlayerNotCosmetics(token.toString());
			String json = new Gson().toJson(cosmetics);
		    response.setContentType("application/json");
		    response.setCharacterEncoding("UTF-8");
		    response.setStatus(200);
		    response.getWriter().write(json);
			request.setAttribute( "cosmetics", json );
	        
	        /* 	mettre la reponse de la req sql dans la session */
	        session.setAttribute( "jsonCosmetic", null );
        }
		this.getServletContext().getRequestDispatcher( "/WEB-INF/pages/store.jsp" ).forward( request, response );
	}
	
	public void doPost( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {
		/* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();
        
        /* RECUP L ID DU COSMETICS A BUY */
        
        boolean buyed = cosmeticDao.buyCosmetic(session.getAttribute("token").toString() ,request.getParameter("idCosmetic"));
        /* 	FAIRE LA REQUETE POUR RECUP TOUS LES COSMETICS SAUF CEUX DE LA TABLE PLAYERS*/
        
        ArrayList<Cosmetic> cosmetics = cosmeticDao.readPlayerNotCosmetics(session.getAttribute("token").toString());
		String json = new Gson().toJson(cosmetics);
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
	    response.setStatus(200);
	    response.getWriter().write(json);
		request.setAttribute( "cosmetics", json );
        
        
        if(!buyed)
        	request.setAttribute("buy", "Vous n'avez pas assez d'argent");
        else 
        	request.setAttribute("buy", "Vous avez acheté le cosmetic !");
       
        
        /* 	mettre la reponse de la req sql dans la session */
        session.setAttribute( "jsonCosmetic", null );
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/pages/store.jsp" ).forward( request, response );
	}
	
	
}
