package com.excilys.cdb.dao;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.entities.Company;
import com.excilys.cdb.entities.Computer;
import com.excilys.cdb.mappers.ComputerMapper;
import com.excilys.cdb.util.UtilDate;

@Repository("computerDAO")
@Scope("singleton")
public class ComputerDAO implements UtilDate {

    // QUERIES
    private static final String COMPUTER_TABLE = "computer";
    private final String ALL_COMPUTERS = "SELECT c.id, c.name, c.introduced, c.discontinued, o.id, o.name FROM "
            + COMPUTER_TABLE + " c LEFT JOIN company"
            + " o ON c.company_id = o.id ";
    private final String ALL_COMPUTERS_P = ALL_COMPUTERS + " LIMIT ?, ?";
    private final String COMPUTER_BY_ID = ALL_COMPUTERS + " WHERE c.id = ?";
    /*
     * private final String CREATE_COMPUTER = "INSERT INTO " + COMPUTER_TABLE +
     * " (name, introduced, discontinued, company_id ) VALUES ( ?, ?, ?, ?)";
     */
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

    @Autowired
    @Qualifier("computerMapper")
    private ComputerMapper computerMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private DataSource dataSource;

    /**
     * ComputerDAO constructor with datasource injection.
     *
     * @param dataSource
     *            autowired datasource
     */
    @Autowired
    public ComputerDAO(DataSource dataSource) {
        this.dataSource = dataSource;
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
        try {
            // Args for query building
            Object[] args = { offset, limit };
            // query execution
            computers = (ArrayList<Computer>) jdbcTemplate
                    .query(ALL_COMPUTERS_P, args, computerMapper);
        } catch (DataAccessException e) {
            LOGGER.error(
                    "ComputerDAO says : DataAccessException in getComputerList "
                            + e.getMessage());
            // TODO wrap in computerDAOException
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
        Computer computer = null;
        try {
            // Args for query building
            Object[] args = { id };
            // query execution
            computer = jdbcTemplate.queryForObject(COMPUTER_BY_ID, args,
                    computerMapper);
        } catch (DataAccessException e) {
            LOGGER.error(
                    "ComputerDAO says : DataAccessException in getComputerDetail "
                            + e.getMessage());
            // TODO wrap in computerDAOException
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
        // Args for query building
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName(COMPUTER_TABLE).usingGeneratedKeyColumns("id")
                .usingColumns("name", "introduced", "discontinued",
                        "company_id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", computer.getName());
        LocalDate date = computer.getIntroduced();
        parameters.put("introduced", date == null ? null : Date.valueOf(date));
        date = computer.getDiscontinued();
        parameters.put("discontinued",
                date == null ? null : Date.valueOf(date));
        Company company = computer.getCompany();
        parameters.put("company_id", company == null ? null : company.getId());
        try {
            // Query execution
            Long id = jdbcInsert.executeAndReturnKey(parameters).longValue();
            computer.setId(id);
        } catch (DataAccessException e) {
            LOGGER.error(
                    "ComputerDAO says : DataAccessException in createComputer "
                            + e.getMessage());
            // TODO wrap in computerDAOException
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
        Object[] args = { computer.getName(),
                (computer.getIntroduced() == null ? null
                        : Date.valueOf(computer.getIntroduced())),
                (computer.getDiscontinued() == null ? null
                        : Date.valueOf(computer.getDiscontinued())),
                (computer.getCompany() == null ? null
                        : computer.getCompany().getId()),
                computer.getId() };
        try {
            jdbcTemplate.update(UPDATE_COMPUTER, args);
        } catch (DataAccessException e) {
            LOGGER.error(
                    "ComputerDAO says : DataAccessException in updateComputer "
                            + e.getMessage());
            // TODO wrap in computerDAOException
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
        Object[] args = { id };
        try {
            jdbcTemplate.update(DELETE_COMPUTER, args);
        } catch (DataAccessException e) {
            LOGGER.error(
                    "ComputerDAO says : DataAccessException in deleteComputer "
                            + e.getMessage());
            // TODO wrap in computerDAOException
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
        try {
            count = jdbcTemplate.queryForObject(COUNT_COMPUTERS, Integer.class);
        } catch (DataAccessException e) {
            LOGGER.error("ComputerDAO says : DataAccessException in getCount "
                    + e.getMessage());
            // TODO wrap in computerDAOException
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
            String order, String asc) {
        LOGGER.debug("f_findByName");
        List<Computer> computers = null;
        // prepare query
        String request = String.format(FIND_BY_NAME, order, asc);
        // Args for query building
        Object[] args = { "%" + search + "%", offset, limit };
        try {
            // query execution
            computers = jdbcTemplate.query(request, args, computerMapper);
        } catch (DataAccessException e) {
            LOGGER.error("ComputerDAO says : DataAccessException in findByName "
                    + e.getMessage());
            // TODO wrap in computerDAOException
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
        Object[] args = { "%" + search + "%" };
        try {
            count = jdbcTemplate.queryForObject(COUNT_SEARCH_RESULT, args,
                    Integer.class);
        } catch (DataAccessException e) {
            LOGGER.error(
                    "ComputerDAO says : DataAccessException in getSearchCount "
                            + e.getMessage());
            // TODO wrap in computerDAOException
        }
        return count;
    }

    /**
     * Delete all computers which are provided by a company.
     *
     * @param companyId
     *            the id of the company
     */
    public void deleteComputersByCompany(long companyId) {
        Object[] args = { companyId };
        try {
            int count = jdbcTemplate.update(DELETE_BY_COMPANY, args);
            if (count > 0) {
                System.out.println(count + " computers deleted !");
                LOGGER.debug(count + " computers deleted !");
            } else {
                System.out.println("Fail to delete computers with company_id = "
                        + companyId);
                LOGGER.error("Fail to delete computers with company_id = "
                        + companyId);
            }
        } catch (DataAccessException e) {
            LOGGER.error(
                    "ComputerDAO says : DataAccessException in deleteComputersByCompany "
                            + e.getMessage());
            throw new DAOException();
        }
    }
}
