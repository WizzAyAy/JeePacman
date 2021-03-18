package com.dao;

import com.bean.User;
import com.dao.exceptions.DAOException;
import com.dao.interfaces.PlayerDAO;

public class PlayerDAOImpl implements PlayerDAO {
	private DAOFactory          daoFactory;

    PlayerDAOImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }
    

	@Override
	public void create(User player) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User read(String email) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(String email) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String email) throws DAOException {
		// TODO Auto-generated method stub
		
	}

}
