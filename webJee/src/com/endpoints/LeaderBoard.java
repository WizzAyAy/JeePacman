package com.endpoints;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet( name="LeaderBoard", urlPatterns = "/leaderboard" )
public class LeaderBoard extends HttpServlet  {
	
	
	public void doGet( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {
	
		this.getServletContext().getRequestDispatcher( "/WEB-INF/pages/leaderboard.jsp" ).forward( request, response );

	}
}
