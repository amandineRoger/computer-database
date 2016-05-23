package com.excilys.cdb.control;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("connectionManager")
@Scope("singleton")
public class ConnectionManager {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ConnectionManager.class);

    @Autowired
    @Qualifier("connectionFactory")
    private ConnectionFactory connectionFactory;
    private ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    public void init() {
        if (threadLocal.get() == null) {
            LOGGER.error("init failed ! ");
            // TODO throw Connection exception
        }
        Connection connect = connectionFactory.getConnection();
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
