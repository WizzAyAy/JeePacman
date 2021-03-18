package com.dao.interfaces;

import com.bean.Cosmetic;
import com.dao.exceptions.DAOException;

public interface CosmeticsDAO {
	void create(Cosmetic cosmetic) throws DAOException;
	Cosmetic read(String name) throws DAOException;
	void update(String name) throws DAOException;
	void delete(String name) throws DAOException;
}
