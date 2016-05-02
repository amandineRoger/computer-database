package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.entities.Computer;
import com.excilys.cdb.mappers.ComputerMapper;
import com.excilys.cdb.util.UtilDate;
import com.excilys.cdb.util.UtilQuerySQL;

public enum ComputerDAO implements UtilDate {

    INSTANCE;

    // QUERIES
    private final String COMPUTER_TABLE = "computer";
    private final String ALL_COMPUTERS = "SELECT c.id, c.name, c.introduced, c.discontinued, o.id, o.name FROM "
            + COMPUTER_TABLE + " c LEFT JOIN " + UtilQuerySQL.COMPANY_TABLE
            + " o ON c.company_id = o.id ";
    private final String ALL_COMPUTERS_P = ALL_COMPUTERS + " LIMIT ?, ?";
    private final String COMPUTER_BY_ID = ALL_COMPUTERS + " WHERE c.id = ?";
    private final String CREATE_COMPUTER = "INSERT INTO " + COMPUTER_TABLE
            + " (name, introduced, discontinued, company_id ) VALUES ( ?, ?, ?, ?)";
    private final String UPDATE_COMPUTER = "UPDATE " + COMPUTER_TABLE
            + " SET name = ?, introduced = ?,"
            + "discontinued = ?, company_id = ? WHERE id = ?";
    private final String DELETE_COMPUTER = "DELETE FROM " + COMPUTER_TABLE
            + " WHERE id = ?";
    private final String COUNT_COMPUTERS = "SELECT COUNT(*) FROM "
            + COMPUTER_TABLE;

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ComputerDAO.class);

    private static SingleConnect singleConnect;
    private static Connection connect;
    private static ComputerMapper computerMapper;

    static {
        singleConnect = SingleConnect.INSTANCE;
        computerMapper = ComputerMapper.INSTANCE;
    }

    /**
     * Get computers from select request.
     *
     * @param offset
     *            offset in request
     * @param limit
     *            number of results
     * @return computers from offset+1 to offset+limit+1;
     */
    public ArrayList<Computer> getComputerList(int offset, int limit) {
        LOGGER.debug("f_getComputerList");
        connect = singleConnect.getConnection();
        ArrayList<Computer> computers = new ArrayList<>(limit);
        ResultSet results = null;
        PreparedStatement ps = null;
        try {
            // query execution
            ps = connect.prepareStatement(ALL_COMPUTERS_P);
            ps.setInt(1, offset);
            ps.setInt(2, limit);
            results = ps.executeQuery();
            // Mapping
            computers = (ArrayList<Computer>) computerMapper
                    .convertResultSet(results);
        } catch (SQLException e) {
            LOGGER.error("ComputerDAO says : SQLException in getComputerList "
                    + e.getMessage());
            // TODO wrap in computerDAOException
        } finally {
            singleConnect.closeObject(ps);
            singleConnect.closeObject(results);
            singleConnect.closeObject(connect);
        }
        return computers;
    }

    /**
     * Find a computer by its id.
     *
     * @param id
     *            the id of the wanted computer
     * @return the wanted computer
     */
    public Computer getComputerDetail(long id) {
        LOGGER.debug("f_getComputerDetail");
        connect = singleConnect.getConnection();
        Computer computer = null;
        ResultSet results = null;
        PreparedStatement ps = null;

        try {
            // query execution
            ps = connect.prepareStatement(COMPUTER_BY_ID);
            ps.setLong(1, id);
            results = ps.executeQuery();
            // Mapping
            computer = computerMapper.toEntity(results);
        } catch (SQLException e) {
            LOGGER.error("ComputerDAO says : SQLException in getComputerDetail "
                    + e.getMessage());
            // TODO wrap in computerDAOException
        } finally {
            singleConnect.closeObject(ps);
            singleConnect.closeObject(results);
            singleConnect.closeObject(connect);
        }
        return computer;
    }

    /**
     * Create a computer from user entry.
     *
     * @param computer
     *            the entity newly created
     *
     * @return created computer
     */
    public Computer createComputer(Computer computer) {
        LOGGER.debug("f_createComputer");
        connect = singleConnect.getConnection();
        PreparedStatement ps = null;
        ResultSet results = null;
        try {
            // Query execution
            ps = connect.prepareStatement(CREATE_COMPUTER,
                    Statement.RETURN_GENERATED_KEYS);
            computerMapper.attachEntityToRequest(ps, computer, true);
            ps.executeUpdate();
            results = ps.getGeneratedKeys();
            results.next();
            computer.setId(results.getLong(1));
        } catch (SQLException e) {
            LOGGER.error("ComputerDAO says : SQLException in createComputer "
                    + e.getMessage());
            // TODO wrap in computerDAOException
        } finally {
            singleConnect.closeObject(ps);
            singleConnect.closeObject(results);
            singleConnect.closeObject(connect);
        }
        return computer;
    }

    /**
     * Update a computer (user choice).
     *
     * @param computer
     *            the updated computer (Caution! update is based on computer.id)
     *
     * @return updated computer
     */
    public Computer updateComputer(Computer computer) {
        LOGGER.debug("f_updateComputer");
        // Query execution
        connect = singleConnect.getConnection();
        PreparedStatement ps = null;
        try {
            ps = connect.prepareStatement(UPDATE_COMPUTER);
            computerMapper.attachEntityToRequest(ps, computer, false);
            ps.executeUpdate();
            ps.close();
            connect.close();
        } catch (SQLException e) {
            LOGGER.error("ComputerDAO says : SQLException in updateComputer "
                    + e.getMessage());
            // TODO wrap in computerDAOException
        } finally {
            singleConnect.closeObject(ps);
            singleConnect.closeObject(connect);
        }
        return computer;
    }

    /**
     * Delete a computer by its id.
     *
     * @param id
     *            id of wanted computer to delete
     * @return deleted computer
     */
    public Computer deleteComputer(long id) {
        LOGGER.debug("f_deleteComputer");
        Computer computer = getComputerDetail(id);
        PreparedStatement ps = null;
        // query execution
        try {
            connect = singleConnect.getConnection();
            ps = connect.prepareStatement(DELETE_COMPUTER);
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("ComputerDAO says : SQLException in deleteComputer "
                    + e.getMessage());
            // TODO wrap in computerDAOException
        } finally {
            singleConnect.closeObject(ps);
            singleConnect.closeObject(connect);
        }
        return computer;
    }

    /**
     * Get the number of computers in database.
     *
     * @return number of computers in database
     */
    public int getCount() {
        int count = 0;
        PreparedStatement ps = null;
        ResultSet results = null;
        try {
            connect = singleConnect.getConnection();
            ps = connect.prepareStatement(COUNT_COMPUTERS);
            results = ps.executeQuery();
            if (results.next()) {
                count = results.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error("ComputerDAO says : SQLException in getCount "
                    + e.getMessage());
            // TODO wrap in computerDAOException
        } finally {
            singleConnect.closeObject(ps);
            singleConnect.closeObject(results);
            singleConnect.closeObject(connect);
        }
        return count;
    }
}
