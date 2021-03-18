package com.dao;

import com.bean.Game;
import com.dao.exceptions.DAOException;
import com.dao.interfaces.GamesDAO;

public class GamesDAOImpl implements GamesDAO{
	private DAOFactory          daoFactory;

    GamesDAOImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }
    
    
	@Override
	public void create(Game game) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Game read(Integer id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Integer id) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer id) throws DAOException {
		// TODO Auto-generated method stub
		
	}

}
