package com.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.dao.exceptions.DAOConfigurationException;

public class DAOFactory {

    private static final String FICHIER_PROPERTIES      = "/com/dao/dao.properties";
    private static final String PROPERTY_URL            = "url";
    private static final String PROPERTY_DRIVER         = "driver";
    private static final String PROPERTY_USERNAME 		= "username";
    private static final String PROPERTY_PASSWORD    	= "password";

    private String url;
    private String username;
    private String password;

    DAOFactory(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /*
     * Méthode chargée de récupérer les informations de connexion à la base de
     * données, charger le driver JDBC et retourner une instance de la Factory
     */
    public static DAOFactory getInstance() throws DAOConfigurationException {
        Properties properties = new Properties();
        String url;
        String driver;
        String username;
        String password;

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream fichierProperties = classLoader.getResourceAsStream( FICHIER_PROPERTIES );

        if ( fichierProperties == null ) {
            throw new DAOConfigurationException( "Properties file " + FICHIER_PROPERTIES + " not found." );
        }

        try {
            properties.load( fichierProperties );
            url = properties.getProperty( PROPERTY_URL );
            driver = properties.getProperty( PROPERTY_DRIVER );
            username = properties.getProperty( PROPERTY_USERNAME );
            password = properties.getProperty( PROPERTY_PASSWORD );
        } catch ( IOException e ) {
            throw new DAOConfigurationException( "Can't load properties file " + FICHIER_PROPERTIES, e );
        }

        try {
            Class.forName(driver);
        } catch ( ClassNotFoundException e ) {
            throw new DAOConfigurationException( "Driver unfindable in the classpath.", e );
        }

        DAOFactory instance = new DAOFactory( url, username, password );
        return instance;
    }

    /* Méthode chargée de fournir une connexion à la base de données */
    Connection getConnection() throws SQLException {
        return DriverManager.getConnection( url, username, password );
    }

    /*
     * Méthodes de récupération de l'implémentation des différents DAO 
     */
    public PlayerDAOImpl getPlayerDao() {
        return new PlayerDAOImpl(this);
    }
    
    public CosmeticsDAOImpl getCosmeticsDao() {
        return new CosmeticsDAOImpl(this);
    }
    
    public GamesDAOImpl getGamesDao() {
        return new GamesDAOImpl(this);
    }
}
