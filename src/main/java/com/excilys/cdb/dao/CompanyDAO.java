package com.excilys.cdb.dao;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.entities.Company;
import com.excilys.cdb.mappers.CompanyMapper;

/**
 * Data Access Object for Company objects and requests Singleton pattern.
 *
 * @author Amandine Roger
 *
 */
@Repository("companyDAO")
@Scope("singleton")
public class CompanyDAO {

    // Queries
    String COMPANY_TABLE = "company";
    String ALL_COMPANIES = "SELECT id, name FROM " + COMPANY_TABLE;
    String ALL_COMPANIES_P = ALL_COMPANIES + " LIMIT ?, ?";
    String COMPANY_BY_ID = ALL_COMPANIES + " WHERE id = ?";
    String COUNT_COMPANIES = "SELECT COUNT(*) FROM " + COMPANY_TABLE;
    String DELETE_COMPANY = "DELETE FROM " + COMPANY_TABLE + " WHERE id = ?";

    private static final Logger LOGGER = LoggerFactory
            .getLogger(CompanyDAO.class);
    private static final String TAG = "CompanyDAO says _ ";

    @Autowired
    @Qualifier("companyMapper")
    private CompanyMapper companyMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private DataSource dataSource;

    /**
     * CompanyDAO constructor with datasource injection.
     *
     * @param dataSource
     *            autowired datasource
     */
    @Autowired
    public CompanyDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Get companies from select request.
     *
     * @param offset
     *            offset in request
     * @param limit
     *            number of results
     * @return companies from offset+1 to offset+limit+1;
     */
    public List<Company> getCompanyList(final int offset, final int limit) {
        LOGGER.debug(TAG + "f_getCompanyList");
        List<Company> list = null;
        // ARGS for query building
        Object[] args = { offset, limit };
        try {
            // Query execution
            list = jdbcTemplate.query(ALL_COMPANIES_P, args, companyMapper);
        } catch (DataAccessException e) {
            LOGGER.error(TAG + "DataAccessException in getCompanyList "
                    + e.getMessage());
            throw new DAOException(e);
        }
        return list;
    }

    /**
     * Get companies from select * request.
     *
     * @return List of all companies
     */
    public List<Company> getAllCompanyList() {
        LOGGER.debug(TAG + "f_getAllCompanyList");
        List<Company> list = null;
        try {
            list = jdbcTemplate.query(ALL_COMPANIES, companyMapper);
        } catch (DataAccessException e) {
            LOGGER.error(TAG + "DataAccessException in getAllCompanyList "
                    + e.getMessage());
            throw new DAOException(e);
        }
        return list;
    }

    /**
     * Find a company by its id.
     *
     * @param id
     *            id of the wanted company
     * @return wanted company
     */
    public Company getCompanyById(long id) {
        LOGGER.debug(TAG + "f_getCompanyById");
        Company company = null;
        if (id != 0) {
            // Args for query building
            Object[] args = { id };
            try {
                // Query execution
                company = jdbcTemplate.queryForObject(COMPANY_BY_ID, args,
                        companyMapper);
            } catch (DataAccessException e) {
                System.out
                        .println(TAG + "DataAccessException in getCompanyById "
                                + e.getMessage());
                throw new DAOException(e);
            }
        }
        return company;
    }

    /**
     * Get the number of companies in database.
     *
     * @return number of companies in database
     */
    public int getCount() {
        LOGGER.debug(TAG + "f_getCount");
        int count = 0;
        try {
            // Query execution
            count = jdbcTemplate.queryForObject(COUNT_COMPANIES, Integer.class);
        } catch (DataAccessException e) {
            System.out.println(
                    TAG + "DataAccessException in getCount " + e.getMessage());
            throw new DAOException(e);
        }
        return count;
    }

    /**
     * delete a company by its id and delete all computers from this company.
     * Transaction handling !
     *
     * @param id
     *            id of the company to delete
     */
    public void deleteCompany(long id) {
        LOGGER.debug(TAG + "f_deleteCompany");

        int count = 0;
        // Args for query building
        Object[] args = { id };
        try {
            // Query execution
            count = jdbcTemplate.update(DELETE_COMPANY, args);
            if (count > 0) {
                System.out
                        .println("Company " + id + " was successfully deleted");
                LOGGER.debug(
                        TAG + "company " + id + " was successfully deleted");
            } else {
                System.out.println("Fail to delete company " + id);
                LOGGER.error(TAG + "Fail to delete company " + id);
            }
        } catch (DataAccessException e) {
            LOGGER.error(TAG + "DataAccessException in deleteCompany "
                    + e.getMessage());
            throw new DAOException(e);
        }
    }
}
