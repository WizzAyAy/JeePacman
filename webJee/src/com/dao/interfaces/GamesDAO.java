package com.dao.interfaces;

import com.bean.Game;
import com.dao.exceptions.DAOException;

public interface GamesDAO {
	void create(Game game) throws DAOException;
	Game read(Integer id) throws DAOException; // Having another one to find by Player id would be great
	void update(Game game) throws DAOException;
	void delete(Integer id) throws DAOException;
}
