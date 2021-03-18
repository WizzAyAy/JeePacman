package com.endpoints;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet( name="MyGame", urlPatterns = "/mygame" )
public class MyGame extends HttpServlet  {
	
	public void doGet( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher( "/WEB-INF/pages/myGames.jsp" ).forward( request, response );
	}
}
