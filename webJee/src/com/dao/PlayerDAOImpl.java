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
	private static final String SQL_SELECT_USER = "SELECT id, email, pseudo, password FROM Player WHERE token = ?";
	
	private static final String SQL_SELECT_BY_EMAIL = "SELECT id, email, pseudo, password FROM Player WHERE email = ?";
	
	private static final String SQL_SELECT_GAME_PLAYERS = "SELECT * from Player WHERE id in "
			+ "(SELECT DISTINCT idPlayer from GamePlayers WHERE idGame=?)";
	
	private static final String SQL_CREATE_PLAYER = "INSERT INTO Player (email, password, pseudo, token) VALUES (?, ?, ?, ?)";

	
	private static final String SELECT_COUNT_EMAIL = "SELECT COUNT(email) FROM Player WHERE email = ?";
	
	private static final String SELECT_COUNT_PSEUDO = "SELECT COUNT(pseudo) FROM Player WHERE pseudo = ?";
	
	private static final String SQL_UPDATE_PLAYER_TOKEN = "UPDATE Player SET token=? WHERE email=?";
	
	private static final String SQL_DELETE_PLAYER = "DELETE FROM Player WHERE email=?";
	
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

	    System.out.println(player.getToken());
	    try {
	        /* Récupération d'une connexion depuis la Factory */
	    	connection = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee(connection, SQL_CREATE_PLAYER, true, player.getEmail(), player.getPassword(), player.getUsername(), player.getToken());
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
	public User read(String token) throws DAOException {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    User user = null;
	    
	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connection = daoFactory.getConnection();
	        
	        // Retrieve user data from database
	        preparedStatement = initialisationRequetePreparee(connection, SQL_SELECT_USER, false, token);
	        resultSet = preparedStatement.executeQuery();
	        
	        // Retrieve cosmetics 
	        ArrayList<Cosmetic> cosmetics = daoFactory.getCosmeticsDao().readPlayerCosmetics(token);
	        
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        if (resultSet.next()) {
	            user = map(resultSet, cosmetics);
	        }
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        closeAll(resultSet, preparedStatement, connection);
	    }

		
		return user;
	}
	
	public ArrayList<User> readGamePlayers(Integer id) {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    
	    ArrayList<User> players = new ArrayList<>();
	    
	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connection = daoFactory.getConnection();
	        
	        // Retrieve user data from database
	        preparedStatement = initialisationRequetePreparee(connection, SQL_SELECT_GAME_PLAYERS, false, id);
	        resultSet = preparedStatement.executeQuery();
	        
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        while (resultSet.next()) {
	            players.add(map(resultSet, daoFactory.getCosmeticsDao().readPlayerCosmetics(resultSet.getString("pseudo"))));
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        closeAll(resultSet, preparedStatement, connection);
	    }

		
		return players;
	}

	@Override
	public void updateToken(User player) throws DAOException {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet incrementalId = null;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	    	connection = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee(connection, SQL_UPDATE_PLAYER_TOKEN, true, player.getToken(), player.getEmail());
	        int statut = preparedStatement.executeUpdate();
	        /* Analyse du statut retourné par la requête d'insertion */
	        if (statut == 0) {
	            throw new DAOException("Échec de mise à jour du token, aucune modification effectuée.");
	        }
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        closeAll(incrementalId, preparedStatement, connection);
	    }
		
	}

	@Override
	public void delete(User player) throws DAOException {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet incrementalId = null;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	    	connection = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee(connection, SQL_DELETE_PLAYER, true, player.getEmail());
	        int statut = preparedStatement.executeUpdate();
	        /* Analyse du statut retourné par la requête d'insertion */
	        if (statut == 0) {
	            throw new DAOException("Échec de suppression, mission aborted.");
	        }
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        closeAll(incrementalId, preparedStatement, connection);
	    }
		
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
	
	// ------------- check if already exists -------------
	public boolean existingEmail(String email) {
		
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    
	    
	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connection = daoFactory.getConnection();
	        
	        // Retrieve user data from database
	        preparedStatement = initialisationRequetePreparee(connection, SELECT_COUNT_EMAIL, false, email);
	        resultSet = preparedStatement.executeQuery();
	        	        
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        if (resultSet.next()) {
	        	if ( resultSet.getString("COUNT(email)").equals("1") ) return true;
	        	else return false;
	        }
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        closeAll(resultSet, preparedStatement, connection);
	    }
	    return false;
	}
	
	public boolean existingUsername(String username) {
		
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    
	    
	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connection = daoFactory.getConnection();
	        
	        // Retrieve user data from database
	        preparedStatement = initialisationRequetePreparee(connection, SELECT_COUNT_PSEUDO, false, username);
	        resultSet = preparedStatement.executeQuery();
	        	        
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        if (resultSet.next()) {
	        	
	        	if ( resultSet.getString("COUNT(pseudo)").equals("1") ) {
	        		
	        		return true;
	        		
	        	}
	        	else return false;
	        }
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        closeAll(resultSet, preparedStatement, connection);
	    }
	    return false;
	}
	
	//- check if good combo email password- //
	public boolean goodIds (String email, String password) {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    
	    
	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connection = daoFactory.getConnection();
	        
	        // Retrieve user data from database
	        preparedStatement = initialisationRequetePreparee(connection, SQL_SELECT_BY_EMAIL, false, email);
	        resultSet = preparedStatement.executeQuery();
	        	        
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        if (resultSet.next()) {
	        	
	        	if ( resultSet.getString("password").equals(password) ) {
	        		return true;
	        	}
	        	else return false;
	        }
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        closeAll(resultSet, preparedStatement, connection);
	    }
	    return false;
	}
}

