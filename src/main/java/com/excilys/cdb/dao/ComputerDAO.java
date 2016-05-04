package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.entities.Computer;
import com.excilys.cdb.mappers.ComputerMapper;
import com.excilys.cdb.util.UtilDate;

public enum ComputerDAO implements UtilDate {

    INSTANCE;

    // QUERIES
    private static final String COMPUTER_TABLE = "computer";
    private final String ALL_COMPUTERS = "SELECT c.id, c.name, c.introduced, c.discontinued, o.id, o.name FROM "
            + COMPUTER_TABLE + " c LEFT JOIN company"
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
    private final String FIND_BY_NAME = ALL_COMPUTERS
            + " WHERE c.name LIKE ? ORDER BY %s %s LIMIT ?, ?";
    private final String COUNT_SEARCH_RESULT = "SELECT COUNT(*) FROM "
            + COMPUTER_TABLE + " WHERE name LIKE ?";
    public static final String DELETE_BY_COMPANY = "DELETE FROM "
            + COMPUTER_TABLE + " WHERE company_id = ?";

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
        LOGGER.debug("f_getCount");
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

    /**
     * Search computers by name.
     *
     * @param search
     *            the name to search
     * @param offset
     *            offset to put in query
     * @param limit
     *            number of results
     * @param order
     *            field used for order results
     * @param asc
     *            true if ascendant order, false else
     * @return all computers which name contains search
     */
    public List<Computer> findByName(String search, int offset, int limit,
            String order, boolean asc) {
        LOGGER.debug("f_findByName");
        List<Computer> computers = new ArrayList<>();

        // prepare query
        String request = null;
        switch (order) {
            case "name":
                request = "c.name";
                break;
            case "introduced":
                request = "c.introduced";
                break;
            case "discontinued":
                request = "c.discontinued";
                break;
            case "company_id":
                request = "o.id";
                break;
            case "id":
            default:
                request = "c.id";
                break;
        }
        if (!asc) {
            request = String.format(FIND_BY_NAME, request, "DESC");
        } else {
            request = String.format(FIND_BY_NAME, request, "ASC");
        }

        connect = singleConnect.getConnection();
        PreparedStatement ps = null;
        ResultSet results = null;

        try {
            // query execution
            ps = connect.prepareStatement(request);
            ps.setString(1, "%" + search + "%");
            ps.setInt(2, offset);
            ps.setInt(3, limit);

            results = ps.executeQuery();
            // Mapping
            computers = computerMapper.convertResultSet(results);
        } catch (SQLException e) {
            LOGGER.error("ComputerDAO says : SQLException in findByName "
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
     * Get the number of computers which correspond to search.
     *
     * @param search
     *            search parameter
     * @return the number of computers
     */
    public int getSearchCount(String search) {
        LOGGER.debug("f_getSearchCount");
        int count = 0;
        PreparedStatement ps = null;
        ResultSet results = null;

        try {
            connect = singleConnect.getConnection();
            ps = connect.prepareStatement(COUNT_SEARCH_RESULT);
            ps.setString(1, "%" + search + "%");
            results = ps.executeQuery();
            if (results.next()) {
                count = results.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.error("ComputerDAO says : SQLException in getSearchCount "
                    + e.getMessage());
            // TODO wrap in computerDAOException
        } finally {
            singleConnect.closeObject(ps);
            singleConnect.closeObject(results);
            singleConnect.closeObject(connect);
        }
        return count;
    }

    /**
     * Delete all computers which are provided by a company
     *
     * @param companyId
     *            the id of the company
     * @param connection
     *            connection provided by service, (autocommit has to be set to
     *            false if you want to roll back!)
     */
    public void deleteComputersByCompany(long companyId,
            Connection connection) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(DELETE_BY_COMPANY);
            ps.setLong(1, companyId);
            int count = ps.executeUpdate();
            if (count > 0) {
                System.out.println(count + " computers deleted !");
                LOGGER.debug(count + " computers deleted !");
            } else {
                System.out.println("Fail to delete computers with company_id = "
                        + companyId);
                LOGGER.error("Fail to delete computers with company_id = "
                        + companyId);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException in deleteComputersByCompany "
                    + e.getMessage());
            throw new DAOException();
        } finally {
            singleConnect.closeObject(ps);
        }
    }
}
