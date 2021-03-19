package com.dao.utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bean.User;

// Full openclassroom style
public class DAOutilities {

	//-------------- Prepared Statements
	
	public static PreparedStatement initialisationRequetePreparee(Connection connection, String sql, 
			boolean returnGeneratedKeys, Object... objects) throws SQLException {
		
	    PreparedStatement preparedStatement = connection.prepareStatement(sql, 
	    		returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
	    
	    for ( int i = 0; i < objects.length; i++ ) 
	    {
	        preparedStatement.setObject( i + 1, objects[i] );
	    }
	    return preparedStatement;
	}
	
	
	//------------- Closing sql resources
	
	public static void closeResultSet(ResultSet resultSet) {
	    if ( resultSet != null ) {
	        try {
	            resultSet.close();
	        } catch ( SQLException e ) {
	            System.out.println( "Can't close ResultSet : " + e.getMessage() );
	        }
	    }
	}

	public static void closeStatement(Statement statement) {
	    if ( statement != null ) {
	        try {
	            statement.close();
	        } catch ( SQLException e ) {
	            System.out.println( "Can't close Statement : " + e.getMessage() );
	        }
	    }
	}

	
	public static void closeConnection(Connection connection) {
	    if ( connection != null ) {
	        try {
	            connection.close();
	        } catch ( SQLException e ) {
	            System.out.println( "Can't close connection : " + e.getMessage() );
	        }
	    }
	}

	/* Fermetures silencieuses du statement et de la connection */
	public static void closeAll( Statement statement, Connection connection ) {
	    closeStatement( statement );
	    closeConnection( connection );
	}

	/* Fermetures silencieuses du resultset, du statement et de la connection */
	public static void closeAll( ResultSet resultSet, Statement statement, Connection connection ) {
	    closeResultSet( resultSet );
	    closeStatement( statement );
	    closeConnection( connection );
	}
}
