package com.dao;

import com.bean.Cosmetic;
import com.dao.exceptions.DAOException;
import com.dao.interfaces.CosmeticsDAO;

public class CosmeticsDAOImpl implements CosmeticsDAO{
	private DAOFactory          daoFactory;

    CosmeticsDAOImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }
    
    
	@Override
	public void create(Cosmetic cosmetic) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Cosmetic read(String name) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(String name) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String name) throws DAOException {
		// TODO Auto-generated method stub
		
	}

}
