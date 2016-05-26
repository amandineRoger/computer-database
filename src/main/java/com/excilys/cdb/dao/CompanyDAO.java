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
        LOGGER.debug("f_getCompanyList");
        List<Company> list = null;
        // ARGS for query building
        Object[] args = { offset, limit };
        try {
            // Query execution
            list = jdbcTemplate.query(ALL_COMPANIES_P, args, companyMapper);
        } catch (DataAccessException e) {
            LOGGER.error(
                    "CompanyDAO says : DataAccessException in getCompanyList "
                            + e.getMessage());
            // TODO wrap in CompanyDAOException
        }
        return list;
    }

    /**
     * Get companies from select * request.
     *
     * @return List of all companies
     */
    public List<Company> getAllCompanyList() {
        LOGGER.debug("f_getAllCompanyList");
        List<Company> list = null;
        try {
            list = jdbcTemplate.query(ALL_COMPANIES, companyMapper);
        } catch (DataAccessException e) {
            LOGGER.error(
                    "CompanyDAO says : DataAccessException in getAllCompanyList "
                            + e.getMessage());
            // TODO wrap in CompanyDAOException
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
        LOGGER.debug("f_getCompanyById");
        Company company = null;
        if (id != 0) {
            // Args for query building
            Object[] args = { id };
            try {
                // Query execution
                company = jdbcTemplate.queryForObject(COMPANY_BY_ID, args,
                        companyMapper);
            } catch (DataAccessException e) {
                System.out.println(
                        "Company DAO says : DataAccessException in getCompanyById "
                                + e.getMessage());
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
        LOGGER.debug("f_getCount");
        int count = 0;
        try {
            // Query execution
            count = jdbcTemplate.queryForObject(COUNT_COMPANIES, Integer.class);
        } catch (DataAccessException e) {
            System.out.println(
                    "Company DAO says : DataAccessException in getCount "
                            + e.getMessage());
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
        LOGGER.debug("f_deleteCompany");

        int count = 0;
        // Args for query building
        Object[] args = { id };
        try {
            // Query execution
            count = jdbcTemplate.update(DELETE_COMPANY, args);
            if (count > 0) {
                System.out
                        .println("Company " + id + " was successfully deleted");
                LOGGER.debug("company " + id + " was successfully deleted");
            } else {
                System.out.println("Fail to delete company " + id);
                LOGGER.error("Fail to delete company " + id);
            }
        } catch (DataAccessException e) {
            LOGGER.error(
                    "Company DAO says : DataAccessException in deleteCompany "
                            + e.getMessage());
            // TODO wrap in DAOException and throw it
        }
    }
}
