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
import com.modele.ConnexionForm;
import com.modele.CreationForm;

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
        CreationForm form = new CreationForm();

        /* Traitement de la requête et récupération du bean en résultant */
        User utilisateur = form.connecterUtilisateur( request );

        /* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();

        /**
         * Si aucune erreur de validation n'a eu lieu, alors ajout du bean
         * Utilisateur à la session, sinon suppression du bean de la session.
         */
        
        playerDao = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY )).getPlayerDao();

        if(playerDao.existingUsername(request.getParameter( "username" ))) {
        	form.setErreur("username", "username deja dans la base de données");
        }
        
        if(playerDao.existingEmail(request.getParameter( "email" ))) {
        	form.setErreur("email", "email deja dans la base de données");
        }
        
        if ( form.getErreurs().isEmpty() ) {
            session.setAttribute( ATT_SESSION_USER, utilisateur );
        } else {
            session.setAttribute( ATT_SESSION_USER, null );
        }

        /* Stockage du formulaire et du bean dans l'objet request */
        request.setAttribute( ATT_FORM, form );
        request.setAttribute( ATT_USER, utilisateur );
        
        if ( form.getErreurs().isEmpty() ) {
            session.setAttribute( ATT_SESSION_USER, utilisateur );
        } else {
            session.setAttribute( ATT_SESSION_USER, null );
        }
        
       

        /*si aucune erreur on retourne sur la page d'acceuil, si erreur alors on reste sur la page de creation du compte*/
        if ( form.getErreurs().isEmpty() ) {
        	String token = TokenGen.generateNewToken();
        	session.setAttribute(ATT_TOKEN, token);
        	//mettre en bdd le token de la session + cree l'user
        	User user = new User();
        	user.setEmail(request.getParameter( "email" ));
        	user.setPassword(request.getParameter( "motdepasse" ));
        	user.setUsername(request.getParameter( "username" ));
        	playerDao.create(user);
        	
        	
        	this.getServletContext().getRequestDispatcher( VUE_SUCCES ).forward( request, response );
        } else {
        	this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
        }
    }
}