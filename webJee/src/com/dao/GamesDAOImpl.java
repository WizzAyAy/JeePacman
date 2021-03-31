package com.dao;

import static com.dao.utilities.DAOutilities.closeAll;
import static com.dao.utilities.DAOutilities.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.bean.Cosmetic;
import com.bean.Game;
import com.bean.User;
import com.dao.exceptions.DAOException;
import com.dao.interfaces.GamesDAO;

public class GamesDAOImpl implements GamesDAO{
	private DAOFactory daoFactory;
	
	// SQL querries
		private static final String SQL_SELECT_GAME = "SELECT * from Games WHERE id=?";
		
		private static final String SQL_SELECT_GAMES_PLAYER = "SELECT * from Games WHERE id in (SELECT idGame FROM GamePlayers WHERE idPlayer=(SELECT id FROM Player WHERE token=?))";
		
		private static final String SQL_SELECT_GAMES_25_BEST_GAMES = "SELECT * from Games ORDER BY score DESC LIMIT 25";


		
		private static final String SQL_CREATE_GAME = "INSERT INTO Games (score) VALUES (?)";
		
		private static final String SQL_ADD_GAME_PLAYERS = "INSERT INTO GamePlayers (idGame, idPlayer) VALUES (?, (SELECT id FROM Player WHERE pseudo=?))";
		
		// Useless as we won't change the score nor the players of a game already played
		//private static final String SQL_UPDATE_GAME = "UPDATE Games SET score=? WHERE id=?";
		
		private static final String SQL_DELETE_GAME = "DELETE FROM Games WHERE id=?";

		private static final String SQL_SELECT_GAME_PLAYERS = "SELECT * from Player WHERE id in (SELECT DISTINCT idPlayer from GamePlayers WHERE idGame=?)";

    GamesDAOImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }
    
    
	@Override
	public void create(Game game) throws DAOException {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet incrementalId = null;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	    	connection = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee(connection, SQL_CREATE_GAME, true, game.getScore());
	        int statut = preparedStatement.executeUpdate();
	        /* Analyse du statut retourné par la requête d'insertion */
	        if (statut == 0) {
	            throw new DAOException("Échec de la création de la partie, aucune ligne ajoutée dans la table.");
	        }
	        /* Récupération de l'id auto-généré par la requête d'insertion */
	        incrementalId = preparedStatement.getGeneratedKeys();
	        if (incrementalId.next()) {
	            /* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
	            game.setId(incrementalId.getInt(1));
	        } else {
	            throw new DAOException("Échec de la création de la partie en base, aucun ID auto-généré retourné.");
	        }
	        
	        for(User p : game.getUsers())
	        {
	        	addPlayerToGame(game.getId(), p.getUsername());
	        }
	        
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        closeAll(incrementalId, preparedStatement, connection);
	    }
		
	}
	
	public void addPlayerToGame(int idGame, String playerName)
	{
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet incrementalId = null;
	    
	    try {
	        /* Récupération d'une connexion depuis la Factory */
	    	connection = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee(connection, SQL_ADD_GAME_PLAYERS, true, idGame, playerName);
	        int statut = preparedStatement.executeUpdate();
	        /* Analyse du statut retourné par la requête d'insertion */
	        if (statut == 0) {
	            throw new DAOException("Échec de la création de la partie, aucune ligne ajoutée dans la table.");
	        }

	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        closeAll(incrementalId, preparedStatement, connection);
	    }
	}

	@Override
	public Game read(Integer id) throws DAOException {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Game game = null;
	    
	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connection = daoFactory.getConnection();
	        
	        // Retrieve user data from database
	        preparedStatement = initialisationRequetePreparee(connection, SQL_SELECT_GAME, false, id);
	        resultSet = preparedStatement.executeQuery();
	        
	        // Retrieve cosmetics 
	        ArrayList<User> players = daoFactory.getPlayerDao().readGamePlayers(id);
	        
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        if(resultSet.next()) {
	            game = map(resultSet, players);
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        closeAll(resultSet, preparedStatement, connection);
	    }

		
		return game;
	}
	
	public ArrayList<Game> readGame(String token) throws DAOException {
		//pas tres propre et on a qu'une partie des parties jouées
		Connection connection = null;
		
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    
	    Game game = new Game();
	    ArrayList<Game> games = new ArrayList<Game>();
	    
	    try {
	    	/* Récupération d'une connexion depuis la Factory */
	        connection = daoFactory.getConnection();
	        
	        // Retrieve user data from database
	        preparedStatement = initialisationRequetePreparee(connection, SQL_SELECT_GAMES_PLAYER, false, token);
	        resultSet = preparedStatement.executeQuery();
	        
	        if(resultSet.next()) {
	        	ArrayList<User> players = daoFactory.getPlayerDao().readGamePlayers(resultSet.getInt("id"));
	        	game = map(resultSet, players);
	    	    
	    	    games.add(game);
	        }	    	    
	        
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        closeAll(resultSet, preparedStatement, connection);
	    }

		
		return games;
	}
	
	public ArrayList<Game> readBest() throws DAOException {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    
	    ArrayList<Game> games = new ArrayList<Game>();
	    Game game = new Game();
	    
	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connection = daoFactory.getConnection();
	        
	        // Retrieve user data from database
	        preparedStatement = initialisationRequetePreparee(connection, SQL_SELECT_GAMES_25_BEST_GAMES, false);
	        resultSet = preparedStatement.executeQuery();
	        
	        while(resultSet.next()) {
	        	ArrayList<User> players = daoFactory.getPlayerDao().readGamePlayers(resultSet.getInt("id"));
	        	game = map(resultSet, players);
	    	    games.add(game);
	        }    
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        closeAll(resultSet, preparedStatement, connection);
	    }

		
		return games;
	}

	// Useless as we won't change the score nor the players of a game already played
	@Override
	public void update(Game game) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Game game) throws DAOException {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet incrementalId = null;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	    	connection = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee(connection, SQL_DELETE_GAME, true, game.getId());
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
	
	private static Game map(ResultSet resultSet, ArrayList<User> players) throws SQLException {
		
	    Game game = new Game();
	    game.setId(resultSet.getInt("id") );
	    game.setScore(resultSet.getInt("score"));
	    game.setUsers(players);
	    
	    return game;
	}

}
