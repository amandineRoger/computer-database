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

public class ComputerDAO implements UtilQuerySQL, UtilDate {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ComputerDAO.class);
    private static ComputerDAO instance;

    private SingleConnect singleConnect;
    private Connection connect;
    private ComputerMapper computerMapper;

    /**
     * private constructor for ComputerDAO (Singleton pattern).
     */
    private ComputerDAO() {
        LOGGER.debug("f_ComputerDAO constructor");
        this.singleConnect = SingleConnect.getInstance();
        this.computerMapper = ComputerMapper.getInstance();
    }

    /**
     * getInstance (singleton method).
     *
     * @return the unique instance of ComputerDAO
     */
    public static ComputerDAO getInstance() {
        if (instance == null) {
            synchronized (ComputerDAO.class) {
                if (instance == null) {
                    instance = new ComputerDAO();
                }

            }
        }
        return instance;
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
        ArrayList<Computer> computers = new ArrayList<>(limit);
        ResultSet results = null;
        connect = singleConnect.getConnection();

        try {
            PreparedStatement ps = connect.prepareStatement(ALL_COMPUTERS_P);
            ps.setInt(1, offset);
            ps.setInt(2, limit);
            results = ps.executeQuery();
            // Mapping
            computers = (ArrayList<Computer>) computerMapper
                    .convertResultSet(results);

            ps.close();
            results.close();
            connect.close();
        } catch (SQLException e) {
            System.out.println(
                    "ComputerDAO says : SQLException ! " + e.getMessage());
        }
        return computers;
    }

    /***************************************** getCompanyById ******************************************/
    /**
     * Find a computer by its id.
     *
     * @param id
     *            the id of the wanted computer
     * @return the wanted computer
     */
    public Computer getComputerDetail(long id) {
        LOGGER.debug("f_getComputerDetail");
        Computer computer = null;

        ResultSet results = null;
        connect = singleConnect.getConnection();

        try {
            // query execution
            PreparedStatement ps = connect.prepareStatement(COMPUTER_BY_ID);
            ps.setLong(1, id);
            results = ps.executeQuery();
            // Mapping
            computer = computerMapper.convertIntoEntity(results);

            ps.close();
            results.close();
            connect.close();
        } catch (SQLException e) {
            System.out.println(
                    "ComputerDAO says : SQLException ! " + e.getMessage());
        }
        return computer;
    }

    /***********************************************************************************/

    /**
     * Create a computer from user entry.
     *
     * @param computer
     *            TODO
     *
     * @return created computer
     */
    public Computer createComputer(Computer computer) {
        LOGGER.debug("f_createComputer");
        // Computer computer = Master.getComputerFromUser();
        connect = singleConnect.getConnection();

        try {
            // Query execution
            PreparedStatement ps = connect.prepareStatement(CREATE_COMPUTER,
                    Statement.RETURN_GENERATED_KEYS);
            computerMapper.attachEntityToRequest(ps, computer, true);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            computer.setId(rs.getLong(1));

            ps.close();
            rs.close();
            connect.close();
        } catch (SQLException e) {
            System.out.println(
                    "ComputerDAO says : SQLException ! " + e.getMessage());
        }
        return computer;
    }

    /***********************************************************************************/
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
        try {
            PreparedStatement ps = connect.prepareStatement(UPDATE_COMPUTER);
            computerMapper.attachEntityToRequest(ps, computer, false);

            ps.executeUpdate();
            ps.close();
            connect.close();
        } catch (SQLException e) {
            System.out.println(
                    "ComputerDAO says : updateComputer " + e.getMessage());
        }
        return computer;
    }

    /***********************************************************************************/

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

        // query execution
        try {
            connect = singleConnect.getConnection();
            PreparedStatement ps = connect.prepareStatement(DELETE_COMPUTER);
            ps.setLong(1, id);
            ps.executeUpdate();
            ps.close();
            connect.close();
        } catch (SQLException e) {
            System.out.println(
                    "ComputerDAO says: deleteComputer " + e.getMessage());
        }
        return computer;
    }

    /***********************************************************************************/
    /**
     * Get the number of computers in database.
     *
     * @return number of computers in database
     */
    public int getCount() {
        int count = 0;

        try {
            connect = singleConnect.getConnection();
            PreparedStatement ps = connect.prepareStatement(COUNT_COMPUTERS);
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                count = result.getInt(1);
            }
            ps.close();
            result.close();
            connect.close();

        } catch (SQLException e) {
            System.out.println("ComputerDAO says: getCount " + e.getMessage());
        }

        return count;
    }

}
