package com.endpoints.authentification;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.bean.User;
import com.dao.DAOFactory;
import com.dao.PlayerDAOImpl;
import com.endpoints.Utilities;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.modele.ConnexionForm;


@WebServlet( name="Login", urlPatterns = "/login" )
public class Login extends HttpServlet {
	
	
    public static final String ATT_USER         = "utilisateur";
    public static final String ATT_FORM         = "form";
    public static final String ATT_TOKEN        = "token";
    public static final String VUE              = "/WEB-INF/register/login.jsp";
    public static final String VUE_SUCCESS      = "/WEB-INF/pages/home.jsp";
    public static final String VUE_SUCCESS_JSON = "/WEB-INF/pages/loginToken.jsp";
    public static final String CONF_DAO_FACTORY = "daofactory";

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Affichage de la page de connexion */
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {	
    	/* Préparation de l'objet formulaire */
    	System.out.println("requete login en approche ");
        ConnexionForm form = new ConnexionForm();
        
        String email;
        String password;
        
        if(request.getParameter("email") != null)
        {
        	System.out.println("requete app approche ");
        	email = request.getParameter("email").toString();
        	password = request.getParameter("motdepasse").toString();
        	System.out.println("email : " + email +" | password : "+ password);
        }
        else
        {
        	User u = Utilities.getUserFromBody(request);
        	email = u.getEmail();
        	password = u.getPassword();
        }
        
        PlayerDAOImpl playerDao = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY )).getPlayerDao();

        form.connecterUtilisateur(email, password);
        
       	if(form.getErreurs().isEmpty() && playerDao.goodIds(email, password)) {
       		String token = Utilities.generateNewToken();
           	
           	//mettre en bdd le token de la session
           	User user = new User();
           	user.setEmail(email);
           	user.setPassword(password);
           	user.setToken(token);
           	playerDao.updateToken(user);
           	
           	response.setStatus(200); // OK
           	
           	if(request.getHeader("origin") != null && 
           			request.getHeader("origin").equals(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()))
           	{
           		// Use a cookie to store the token (sessions are not meant to be used in a RESTful api)
           		// Token cookie as an age of 1h (You won't even stay that much on the website with so few things to see)
           		Utilities.setCookie(response, "token", token, 3600);
           		
           		// Just adding username to the request to display it on the website
           		// Consumes more than needed but we wanted a pimped websitew
           		String username = playerDao.read(token).getUsername();
            	request.setAttribute("username", username);
            	// Add the username to a cookie to use it later
            	Utilities.setCookie(response, "username", username, 3600);
           		
           		this.getServletContext().getRequestDispatcher( VUE_SUCCESS ).forward( request, response ); 
           	}
           	else
           	{
           		request.setAttribute("token","{\"token\":"+token+"}");
           		this.getServletContext().getRequestDispatcher( VUE_SUCCESS_JSON ).forward( request, response ); 
           	}
         }
       	 else
       	 {
       	 	response.setStatus(401); // Unauthorized
       	 	
       	 	if(request.getHeader("origin") != null && 
        			request.getHeader("origin").equals(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()))
        	{
	           	form.setErreur("goodIds", "mauvais id de connection");
	           	request.setAttribute( ATT_FORM, form );
	           	this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
        	}
         }
    }

}