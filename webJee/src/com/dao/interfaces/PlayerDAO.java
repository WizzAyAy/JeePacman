package com.dao.interfaces;
import com.bean.User;
import com.dao.exceptions.DAOException;

public interface PlayerDAO {
	void create(User player) throws DAOException;
	User read(String pseudo) throws DAOException;
	void updateToken(User player) throws DAOException;
	void delete(User player) throws DAOException;
}
