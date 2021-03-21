package com.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bean.Cosmetic;
import com.bean.User;
import com.dao.exceptions.DAOException;
import com.dao.interfaces.PlayerDAO;
import static com.dao.utilities.DAOutilities.*;

public class PlayerDAOImpl implements PlayerDAO {
	private DAOFactory          daoFactory;
	
	// SQL querries
	private static final String SQL_SELECT_BY_PSEUDO = "SELECT id, email, pseudo, password FROM Player WHERE pseudo = ?";
	
	private static final String SQL_CREATE_PLAYER = "INSERT INTO Player (email, password, pseudo) VALUES (?, ?, ?)";

	
	// -----------------------------
	
	// Constructor
    PlayerDAOImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }
    

	@Override
	public void create(User player) throws DAOException {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet incrementalId = null;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	    	connection = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee(connection, SQL_CREATE_PLAYER, true, player.getEmail(), player.getPassword(), player.getUsername());
	        int statut = preparedStatement.executeUpdate();
	        /* Analyse du statut retourné par la requête d'insertion */
	        if (statut == 0) {
	            throw new DAOException("Échec de la création de l'utilisateur, aucune ligne ajoutée dans la table.");
	        }
	        /* Récupération de l'id auto-généré par la requête d'insertion */
	        incrementalId = preparedStatement.getGeneratedKeys();
	        if ( incrementalId.next() ) {
	            /* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
	            player.setId(incrementalId.getInt(1));
	        } else {
	            throw new DAOException("Échec de la création de l'utilisateur en base, aucun ID auto-généré retourné.");
	        }
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        closeAll(incrementalId, preparedStatement, connection);
	    }
		
	}

	@Override
	public User read(String pseudo) throws DAOException {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    User user = null;
	    
	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connection = daoFactory.getConnection();
	        
	        // Retrieve user data from database
	        preparedStatement = initialisationRequetePreparee(connection, SQL_SELECT_BY_PSEUDO, false, pseudo);
	        resultSet = preparedStatement.executeQuery();
	        
	        // Retrieve cosmetics 
	        ArrayList<Cosmetic> cosmetics = daoFactory.getCosmeticsDao().readPlayerCosmetics(pseudo);
	        
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        if (resultSet.next()) {
	            user = map(resultSet, cosmetics);
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        closeAll(resultSet, preparedStatement, connection);
	    }

		
		return user;
	}

	@Override
	public void update(String pseudo) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String pseudo) throws DAOException {
		// TODO Auto-generated method stub
		
	}
	
	/// ------------------ Mapping data to bean
	
	private static User map(ResultSet resultSet, ArrayList<Cosmetic> cosmetics) throws SQLException {
		
	    User user = new User();
	    user.setId(resultSet.getInt("id") );
	    user.setEmail(resultSet.getString("email") );
	    user.setPassword(resultSet.getString("password"));
	    user.setUsername(resultSet.getString("pseudo"));
	    user.setComestics(cosmetics);
	    
	    return user;
	}
}

