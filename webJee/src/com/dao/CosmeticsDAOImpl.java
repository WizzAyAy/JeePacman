package com.dao;

import static com.dao.utilities.DAOutilities.closeAll;
import static com.dao.utilities.DAOutilities.initialisationRequetePreparee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.bean.Cosmetic;
import com.dao.exceptions.DAOException;
import com.dao.interfaces.CosmeticsDAO;

public class CosmeticsDAOImpl implements CosmeticsDAO{
	private DAOFactory daoFactory;

	// SQL querries
	private static final String SQL_SELECT_PLAYER_COSMETICS = "SELECT * from Cosmetics WHERE id in "
			+ "(SELECT DISTINCT idCosmetic from PlayerCosmetics WHERE idPlayer="
				+ "(SELECT id from Player WHERE pseudo=?))";
	
	private static final String SQL_SELECT_NOT_PLAYER_COSMETICS = "SELECT * from Cosmetics WHERE id not in "
			+ "(SELECT DISTINCT idCosmetic from PlayerCosmetics WHERE idPlayer="
				+ "(SELECT id from Player WHERE pseudo=?))";
	
	private static final String SQL_SELECT_COSMETIC = "SELECT * from Cosmetics WHERE name=?";
	
	private static final String SQL_CREATE_COSMETIC = "INSERT INTO Cosmetics (name, price) VALUES (?, ?)";
	
	private static final String SQL_UPDATE_COSMETIC = "UPDATE Cosmetics SET price=?, name=?";
	
	private static final String SQL_DELETE_COSMETIC = "DELETE FROM Cosmetics WHERE name=?";
	
	// Constructor
    CosmeticsDAOImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }
    
    
	@Override
	public void create(Cosmetic cosmetic) throws DAOException {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet incrementalId = null;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	    	connection = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee(connection, SQL_CREATE_COSMETIC, true, cosmetic.getName(), cosmetic.getPrice());
	        int statut = preparedStatement.executeUpdate();
	        /* Analyse du statut retourné par la requête d'insertion */
	        if (statut == 0) {
	            throw new DAOException("Échec de la création du cosmétique, aucune ligne ajoutée dans la table.");
	        }
	        /* Récupération de l'id auto-généré par la requête d'insertion */
	        incrementalId = preparedStatement.getGeneratedKeys();
	        if (incrementalId.next()) {
	            /* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
	            cosmetic.setId(incrementalId.getInt(1));
	        } else {
	            throw new DAOException("Échec de la création du cosmétique en base, aucun ID auto-généré retourné.");
	        }
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        closeAll(incrementalId, preparedStatement, connection);
	    }
		
	}

	@Override
	public Cosmetic read(String name) throws DAOException {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Cosmetic cosmetic = null;
	    
	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connection = daoFactory.getConnection();
	        
	        // Retrieve user data from database
	        preparedStatement = initialisationRequetePreparee(connection, SQL_SELECT_COSMETIC, false, name);
	        resultSet = preparedStatement.executeQuery();
	        
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        if(resultSet.next()) {
	            cosmetic = map(resultSet);
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        closeAll(resultSet, preparedStatement, connection);
	    }

		
		return cosmetic;
	}
	
	@Override
	public ArrayList<Cosmetic> readPlayerCosmetics(String pseudo) throws DAOException {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    
	    ArrayList<Cosmetic> cosmetics = new ArrayList<>();
	    
	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connection = daoFactory.getConnection();
	        
	        // Retrieve user data from database
	        preparedStatement = initialisationRequetePreparee(connection, SQL_SELECT_PLAYER_COSMETICS, false, pseudo);
	        resultSet = preparedStatement.executeQuery();
	        
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        while (resultSet.next()) {
	            cosmetics.add(map(resultSet));
	        }
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        closeAll(resultSet, preparedStatement, connection);
	    }

		
		return cosmetics;
	}

	@Override
	public void update(Cosmetic cosmetic) throws DAOException {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet incrementalId = null;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	    	connection = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee(connection, SQL_UPDATE_COSMETIC, true, cosmetic.getPrice(), cosmetic.getName());
	        int statut = preparedStatement.executeUpdate();
	        /* Analyse du statut retourné par la requête d'insertion */
	        if (statut == 0) {
	            throw new DAOException("Échec de mise à jour du cosmetique, aucune modification effectuée.");
	        }
	    } catch (SQLException e) {
	        throw new DAOException(e);
	    } finally {
	        closeAll(incrementalId, preparedStatement, connection);
	    }
		
	}

	@Override
	public void delete(Cosmetic cosmetic) throws DAOException {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet incrementalId = null;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	    	connection = daoFactory.getConnection();
	        preparedStatement = initialisationRequetePreparee(connection, SQL_DELETE_COSMETIC, true, cosmetic.getName());
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
	
		private static Cosmetic map(ResultSet resultSet) throws SQLException {
			
		    Cosmetic cosmetic = new Cosmetic();
		    cosmetic.setId(resultSet.getInt("id"));
		    cosmetic.setName(resultSet.getString("name"));
		    cosmetic.setPrice(resultSet.getFloat("price"));
		    
		    return cosmetic;
		}

}
