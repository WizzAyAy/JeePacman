package com.modele;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bean.User;

public final class ConnexionForm {
    private static final String CHAMP_EMAIL  = "email";
    private static final String CHAMP_PASS   = "motdepasse";
    private static final String CHAMP_USERNAME   = "username";

    private String resultat;
    private Map<String, String> erreurs = new HashMap<String, String>();

    public String getResultat() {
        return resultat;
    }

    public Map<String, String> getErreurs() {
        return erreurs;
    }
    
    public void connecterUtilisateur(String email, String password) {

        /* Validation du champ email. */
        try {
            validationEmail( email );
        } catch ( Exception e ) {
            setErreur( CHAMP_EMAIL, e.getMessage() );
        }

        /* Validation du champ mot de passe. */
        try {
            validationMotDePasse(password);
        } catch ( Exception e ) {
            setErreur( CHAMP_PASS, e.getMessage() );
        }

        /* Initialisation du résultat global de la validation. */
        if ( !erreurs.isEmpty() ) {
            resultat = "Échec de la connexion.";
        }
    }

    public void connecterUtilisateur(String email, String password, String username) {

        /* Validation du champ email. */
        try {
            validationEmail( email );
        } catch ( Exception e ) {
            setErreur( CHAMP_EMAIL, e.getMessage() );
        }

        /* Validation du champ mot de passe. */
        try {
            validationMotDePasse(password);
        } catch ( Exception e ) {
            setErreur( CHAMP_PASS, e.getMessage() );
        }
        
        // If username different than null (signin)
        if(username != null)
        {
        	/* Validation du champ username. */
            try {
                validationUsername( username );
            } catch ( Exception e ) {
                setErreur( CHAMP_USERNAME, e.getMessage() );
            }
        }

        /* Initialisation du résultat global de la validation. */
        if ( !erreurs.isEmpty() ) {
            resultat = "Échec de la connexion.";
        }
    }

	/**
     * Valide l'adresse email saisie.
     */
    private void validationEmail( String email ) throws Exception {
    	if ( email == null || email.equals("") )  {throw new Exception( "Merci de saisir une adresse mail." );}
    	
        if ( email != null && !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
            throw new Exception( "Merci de saisir une adresse mail valide." );
        }
    }

    /**
     * Valide le mot de passe saisi.
     */
    private void validationMotDePasse( String motDePasse ) throws Exception {
        if ( motDePasse != null ) {
            if ( motDePasse.length() < 3 ) {
                throw new Exception( "Le mot de passe doit contenir au moins 3 caractères." );
            }
        } else {
            throw new Exception( "Merci de saisir votre mot de passe." );
        }
    }

    /*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    public void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }
    
    /**
     * Valide le mot del'username saisi.
     */
    private void validationUsername( String username ) throws Exception {
        if ( username != null ) {
            if ( username.length() < 3 ) {
                throw new Exception( "L'username doit contenir au moins 3 caractères." );
            }
        } else {
            throw new Exception( "Merci de saisir votre username." );
        }
    }

    /*
     * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
     * sinon.
     */
    private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur;
        }
    }
}