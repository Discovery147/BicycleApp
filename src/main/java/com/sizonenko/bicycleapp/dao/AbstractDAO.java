package com.sizonenko.bicycleapp.dao;

import com.sizonenko.bicycleapp.entity.Entity;
import com.sizonenko.bicycleapp.exception.DAOException;

import java.sql.SQLException;
import java.util.List;

public interface AbstractDAO <K, T extends Entity>{
    List<T> findAll() throws DAOException;
    T findEntityById(K id) throws DAOException;
    boolean delete(K id);
    boolean delete(T entity) throws DAOException;
    boolean create(T entity) throws DAOException;
    boolean update(T entity) throws DAOException;
}
