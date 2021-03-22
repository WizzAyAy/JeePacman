package com.dao.interfaces;
import com.bean.User;
import com.dao.exceptions.DAOException;

public interface PlayerDAO { // Ask if void or type 
	void create(User player) throws DAOException;
	User read(String pseudo) throws DAOException;
	void update(User user) throws DAOException;
	void delete(String email) throws DAOException;
}
