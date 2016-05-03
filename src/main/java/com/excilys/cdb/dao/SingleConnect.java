package com.excilys.cdb.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariDataSource;

public enum SingleConnect {
    INSTANCE;

    private static final Logger LOGGER = LoggerFactory
            .getLogger(SingleConnect.class);

    private static final String PROPERTIES_FILE = "sql.properties";
    private static String url;
    private static String user;
    private static String password;

    // Attributes
    private static HikariDataSource poolSql;

    static {
        try {
            // Load driver jdbc
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            LOGGER.error(
                    "SingleConnect says : ClassNotFoundException in static initialization "
                            + e.getMessage());
            // TODO wrap in ConnectionException
        }
        // Open properties file
        Properties properties = new Properties();
        InputStream propertiesFile = SingleConnect.class.getClassLoader()
                .getResourceAsStream(SingleConnect.PROPERTIES_FILE);
        try {
            properties.load(propertiesFile);
        } catch (IOException e) {
            LOGGER.error(
                    "SingleConnect says : IOException in static initialization "
                            + e.getMessage());
            // TODO wrap in ConnectionException
        }
        // load properties
        url = properties.getProperty("URL") + properties.getProperty("DB_NAME")
                + properties.getProperty("PARAMS");
        user = properties.getProperty("USER");
        password = properties.getProperty("PASSWORD");

        // init connection pool
        SingleConnect.poolSql = new HikariDataSource();
        SingleConnect.poolSql.setJdbcUrl(url);
        SingleConnect.poolSql.setUsername(user);
        SingleConnect.poolSql.setPassword(password);
        // set chache of prepStmts (with recommended value from Hikari)
        SingleConnect.poolSql.addDataSourceProperty("cachePrepStmts", "true");
        // the default is 25
        SingleConnect.poolSql.addDataSourceProperty("prepStmtCacheSize", "10");
        // the default is 256
        SingleConnect.poolSql.addDataSourceProperty("prepStmtCacheSqlLimit",
                "2048");
    }

    // Getters
    /**
     * Open db connection, you must to close it later !.
     *
     * @return java.sql.Connection
     * @throws SQLException
     */
    public Connection getConnection() {
        LOGGER.debug("f_getConnection");
        try {
            return SingleConnect.poolSql.getConnection();
        } catch (SQLException e) {
            LOGGER.error("SingleConnect says : SQLException in getConnection "
                    + e.getMessage());
            // TODO wrap in ConnectionException
        }
        return null; // FIXME to delete when throw exception
    }

    /**
     * Close an Autocloseable object like Statement, ResultSet or Connection.
     *
     * @param o
     *            an autocloseable object
     */
    public void closeObject(AutoCloseable o) {
        LOGGER.debug("f_closeObject");
        try {
            if (o != null) {
                o.close();
            }
        } catch (Exception e) {
            LOGGER.error("Impossible to close object! _ " + e.getMessage());
        }
    }

}
