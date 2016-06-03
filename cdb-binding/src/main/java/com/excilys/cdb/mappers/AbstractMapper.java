package com.excilys.cdb.mappers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * Mapper to convert resultset into entity and vice-versa.
 *
 * @author Amandine Roger
 *
 * @param <T>
 *            type of entity
 */
public interface AbstractMapper<T> {

    int COL_ID = 1;
    int COL_NAME = 2;
    int COL_INTRODUCED = 3;
    int COL_DISCONTINUED = 4;
    int COL_COMPANY_ID = 5;
    int COL_COMPANY_NAME = 6;

    /**
     * Convert resultSet into list of T entities.
     *
     * @param rs
     *            ResultSet from DB request
     * @return list of T entities
     */
    List<T> convertResultSet(ResultSet rs);

    /**
     * Convert resultSet into a T entity.
     *
     * @param rs
     *            ResultSet from DB request
     * @return T entity
     */
    T toEntity(ResultSet rs);

    /**
     * Set parameters into PreparedStatement to request DB (use for update or
     * insert).
     *
     * @param ps
     *            preparedStatement of request to execute
     * @param entity
     *            object that has to be convert into request
     * @param hasToBeCreated
     *            true if preparedStatement is for INSERT request, false if it
     *            is for UPDATE one
     */
    void attachEntityToRequest(PreparedStatement ps, T entity,
            boolean hasToBeCreated);
}
