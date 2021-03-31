package com.endpoints.authentification;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.bean.User;
import com.dao.DAOFactory;
import com.dao.PlayerDAOImpl;
import com.google.gson.Gson;
import com.modele.ConnexionForm;




@WebServlet( name="Login", urlPatterns = "/login" )
public class Login extends HttpServlet {
	
	
    public static final String ATT_USER         = "utilisateur";
    public static final String ATT_FORM         = "form";
    public static final String ATT_TOKEN        = "token";
    public static final String ATT_SESSION_USER = "sessionUtilisateur";
    public static final String VUE              = "/WEB-INF/register/login.jsp";
    public static final String VUE_SUCCES       = "/WEB-INF/pages/home.jsp";
    public static final String CONF_DAO_FACTORY = "daofactory";

    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
        /* Affichage de la page de connexion */
        this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
    }

    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
    	System.out.println(request.getHeader("origin"));
    	        	
    	if(request.getHeader("origin").equals("http://localhost:8080")) {
        	loginWeb(request, response);
        }
    	if(request.getHeader("origin").equals("appPacman")) {
        	try {
				loginApp(request, response);
			} catch (ServletException | IOException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    	
    }
    
    public void loginWeb( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
    	/* Préparation de l'objet formulaire */
        ConnexionForm form = new ConnexionForm();

        /* Traitement de la requête et récupération du bean en résultant */
        User utilisateur = form.connecterUtilisateur( request );

        /* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();
        
        PlayerDAOImpl playerDao = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY )).getPlayerDao();

        if(!playerDao.goodIds(request.getParameter( "email" ), request.getParameter( "motdepasse" ))) {
        	form.setErreur("goodIds", "mauvais id de connection");
        }

        /**
         * Si aucune erreur de validation n'a eu lieu, alors ajout du bean
         * Utilisateur à la session, sinon suppression du bean de la session.
         */
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

        /*si aucune erreur on retourne sur la page d'acceuil, si erreur alors on reste sur la page de connection*/
        if ( form.getErreurs().isEmpty() ) {
        	String token = TokenGen.generateNewToken();
        	session.setAttribute(ATT_TOKEN, token);
        	session.setAttribute("email", request.getParameter( "email" ));
        	//mettre en bdd le token de la session
        	User user = new User();
        	user.setEmail(request.getParameter( "email" ));
        	user.setPassword(request.getParameter( "motdepasse" ));
        	user.setToken(token);
        	
        	playerDao.updateToken(user);

        	
        	this.getServletContext().getRequestDispatcher( VUE_SUCCES ).forward( request, response );        	
        	
        } else {
        	this.getServletContext().getRequestDispatcher( VUE ).forward( request, response );
        }
    }
    
    public void loginApp( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException, ParseException {
    	String body = getBody(request);
    	System.out.println(body);
    	
    	JSONParser parser = new JSONParser();
	    JSONObject ids = (JSONObject) parser.parse(body);
	    
	    System.out.println(ids);
    	
  
        /* Récupération de la session depuis la requête */
        HttpSession session = request.getSession();
        
        PlayerDAOImpl playerDao = ((DAOFactory) getServletContext().getAttribute( CONF_DAO_FACTORY )).getPlayerDao();
        System.out.println(ids.get("email"));
        System.out.println(ids.get("motdepasse"));
        
        if (playerDao.goodIds(ids.get("email").toString(), ids.get("motdepasse").toString())) {
        	String token = TokenGen.generateNewToken();
        	session.setAttribute(ATT_TOKEN, token);

        	//mettre en bdd le token de la session
        	User user = new User();
        	user.setEmail(ids.get("email").toString());
        	user.setPassword(ids.get("motdepasse").toString());
        	user.setToken(token);
        	
        	playerDao.updateToken(user);

        	response.setStatus(200);   
        	System.out.println("oui");
        	
        } else {
        	response.setStatus(401);
        	System.out.println("non");
        }

    	
    }
    
    public static String getBody(HttpServletRequest request) throws IOException {

        String body = null;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
            throw ex;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ex) {
                    throw ex;
                }
            }
        }

        body = stringBuilder.toString();
        return body;
    }

    
    
}