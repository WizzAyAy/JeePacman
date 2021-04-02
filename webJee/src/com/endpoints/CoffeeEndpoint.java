package com.endpoints;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet( name="Coffee", urlPatterns = "/coffee" )
public class CoffeeEndpoint extends HttpServlet  {
	
	public void doGet( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {
		response.setStatus(418); // I'm a teapot
		
		// Add username to response for frontend
		request.setAttribute("username", Utilities.getCookieValue(request, "username"));
		
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/pages/coffee.jsp" ).forward( request, response );
	}
}
