package com.excilys.cdb.control;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum ConnectionManager {
    INSTANCE;

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ConnectionManager.class);

    private static ConnectionFactory connectionFactory;
    private static ThreadLocal<Connection> threadLocal;

    static {
        threadLocal = new ThreadLocal<>();
        connectionFactory = ConnectionFactory.INSTANCE;
    }

    public void init() {
        if (threadLocal.get() == null) {
            LOGGER.error("init failed ! ");
            // TODO throw Connection exception
        }
        Connection connect = ConnectionFactory.INSTANCE.getConnection();
        try {
            connect.setAutoCommit(false);
        } catch (SQLException e) {
            LOGGER.error("impossible to disable autocommit ! ");
            // TODO throw Connection exception
        }
        threadLocal.set(connect);
    }

    public void commit() {
        try {
            threadLocal.get().commit();
        } catch (SQLException e) {
            LOGGER.error("impossible to commit ! ");
            // TODO throw Connection exception
        }

    }

    public void rollback() {
        try {
            threadLocal.get().rollback();
        } catch (SQLException e) {
            LOGGER.error("impossible to rollback ! ");
            // TODO throw Connection exception
        }
    }

    public void close() {
        connectionFactory.closeObject(threadLocal.get());
        threadLocal.remove();
    }

    public Connection get() {
        return threadLocal.get();
    }

}
