package com.endpoints;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet( name="Store", urlPatterns = "/store" )
public class Store extends HttpServlet  {
	
	public void doGet( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {
		/* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();
        
        
        /* 	FAIRE LA REQUETE POUR RECUP TOUS LES COSMETICS SAUF CEUX DE LA TABLE PLAYERS*/
        
        
        /* 	mettre la reponse de la req sql dans la session */
        session.setAttribute( "jsonCosmetic", null );
  
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/pages/store.jsp" ).forward( request, response );
	}
	
	public void doPost( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {
		/* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();
        
        /* RECUP L ID DU COSMETICS A BUY */
        
        request.getParameter("idCosmetic");
        
        /* 	FAIRE LA REQUETE POUR RECUP TOUS LES COSMETICS SAUF CEUX DE LA TABLE PLAYERS*/
        
        if(true)
        	request.setAttribute("buy", "Vous n'avez pas assez d'argent");
        else 
        	request.setAttribute("buy", "Vous avez acheté le cosmetic !");
       
        
        /* 	mettre la reponse de la req sql dans la session */
        session.setAttribute( "jsonCosmetic", null );
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/pages/store.jsp" ).forward( request, response );
	}
	
	
}
