package com.endpoints.authentification;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bean.User;
import com.dao.DAOFactory;
import com.dao.PlayerDAOImpl;
import com.endpoints.Utilities;
import com.modele.ConnexionForm;

@WebServlet( name="Signin", urlPatterns = "/signin" )
public class Signin extends HttpServlet {
	
	public static final String ATT_USER         = "utilisateur";
    public static final String ATT_FORM         = "form";
    public static final String ATT_TOKEN        = "token";
    public static final String ATT_SESSION_USER = "sessionUtilisateur";
    public static final String VUE              = "/WEB-INF/register/signin.jsp";
    public static final String VUE_SUCCES       = "/WEB-INF/pages/home.jsp";
    public static final String CONF_DAO_FACTORY = "daofactory";
	private PlayerDAOImpl playerDao;

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Affichage de la page de connexion */
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Préparation de l'objet formulaire */
    	ConnexionForm form = new ConnexionForm();
    	
    	String email = request.getParameter( "email" );
    	String password = request.getParameter( "motdepasse" );
    	String username = request.getParameter( "username" );
        
        playerDao = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY )).getPlayerDao();
        
        form.connecterUtilisateur(email, password, username);

        if(playerDao.existingUsername(request.getParameter( "username" ))) {
        	form.setErreur("username", "username deja dans la base de données");
        }
        
        if(playerDao.existingEmail(request.getParameter( "email" ))) {
        	form.setErreur("email", "email deja dans la base de données");
        }
        
        /*si aucune erreur on retourne sur la page d'acceuil, si erreur alors on reste sur la page de creation du compte*/
        if (form.getErreurs().isEmpty()) {
        	String token = Utilities.generateNewToken();
        	
        	//mettre en bdd le token de la session + cree l'user
        	User user = new User();
        	user.setEmail(email);
        	user.setPassword(password);
        	user.setUsername(username);
        	user.setToken(token);
        	playerDao.create(user);
        	
        	// Just adding username to the request to display it on the website
        	request.setAttribute("username", username);
        	// Add the username to a cookie to use it later
        	Utilities.setCookie(response, "username", username, 3600);
        	
        	response.setStatus(201); // Created
        	this.getServletContext().getRequestDispatcher( VUE_SUCCES ).forward( request, response );
        } else {
        	response.setStatus(409); // Already exists (for now I consider it's the only way for it not to work)
        	
        	request.setAttribute( ATT_FORM, form );
        	this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
        }
    }
}