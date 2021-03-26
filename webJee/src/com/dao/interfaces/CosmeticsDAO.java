package com.dao.interfaces;

import java.util.ArrayList;

import com.bean.Cosmetic;
import com.dao.exceptions.DAOException;

public interface CosmeticsDAO {
	void create(Cosmetic cosmetic) throws DAOException;
	Cosmetic read(String name) throws DAOException;
	public ArrayList<Cosmetic> readPlayerCosmetics(String pseudo) throws DAOException;
	void update(Cosmetic cosmetic) throws DAOException;
	void delete(Cosmetic cosmetic) throws DAOException;
}
