package com.endpoints.authentification;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.endpoints.Utilities;

@WebServlet( name="Logout", urlPatterns = "/logout" )
public class Logout extends HttpServlet {
	
	public void doGet( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {
		// Delete cookie on logout
		Utilities.setCookie(response, "token", "", 0);
		Utilities.setCookie(response, "username", "", 0);
		this.getServletContext().getRequestDispatcher( "/WEB-INF/pages/home.jsp" ).forward( request, response );
	}
	
}