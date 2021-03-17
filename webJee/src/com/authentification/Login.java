package com.authentification;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Login extends HttpServlet  {
	
	public void doGet( HttpServletRequest request, HttpServletResponse response )	throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher( "/WEB-INF/register/login.jsp" ).forward( request, response );
	}
	
	  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// TODO Auto-generated method stub
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			
			if(username.isEmpty() || password.isEmpty())
			{
				RequestDispatcher req = request.getRequestDispatcher("/WEB-INF/register/signin.jsp");
				req.include(request, response);
			}
			else
			{
				RequestDispatcher req = request.getRequestDispatcher("/WEB-INF/register/register_2.jsp");
				req.forward(request, response);
			}
		}
}
