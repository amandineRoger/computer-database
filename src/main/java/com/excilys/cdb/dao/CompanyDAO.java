package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.control.ConnectionFactory;
import com.excilys.cdb.control.ConnectionManager;
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
    @Qualifier("connectionFactory")
    private ConnectionFactory connectionFactory;
    private Connection connect;
    @Autowired
    @Qualifier("companyMapper")
    private CompanyMapper companyMapper;
    @Autowired
    @Qualifier("connectionManager")
    private ConnectionManager connectionManager;

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
        ArrayList<Company> list = new ArrayList<>(limit);
        PreparedStatement ps = null;
        ResultSet results = null;
        connectionManager.init();
        connect = connectionManager.get();

        try {
            ps = connect.prepareStatement(ALL_COMPANIES_P);
            ps.setInt(1, offset);
            ps.setInt(2, limit);
            results = ps.executeQuery();
            connectionManager.commit();
            list = (ArrayList<Company>) companyMapper.convertResultSet(results);

        } catch (SQLException e) {
            LOGGER.error("CompanyDAO says : SQLException in getCompanyList "
                    + e.getMessage());
            // TODO wrap in CompanyDAOException
        } finally {
            connectionFactory.closeObject(ps);
            connectionFactory.closeObject(results);
            // singleConnect.closeObject(connect);
            connectionManager.close();
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
        ArrayList<Company> list = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet results = null;
        // connect = singleConnect.getConnection();
        connectionManager.init();
        connect = connectionManager.get();

        try {
            ps = connect.prepareStatement(ALL_COMPANIES);
            results = ps.executeQuery();
            connectionManager.commit();
            list = (ArrayList<Company>) companyMapper.convertResultSet(results);
        } catch (SQLException e) {
            LOGGER.error("CompanyDAO says : SQLException in getAllCompanyList "
                    + e.getMessage());
            // TODO wrap in CompanyDAOException
        } finally {
            connectionFactory.closeObject(ps);
            connectionFactory.closeObject(results);
            // singleConnect.closeObject(connect);
            connectionManager.close();
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
            // connect = singleConnect.getConnection();
            connectionManager.init();
            connect = connectionManager.get();
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                ps = connect.prepareStatement(COMPANY_BY_ID);
                ps.setLong(1, id);
                rs = ps.executeQuery();
                connectionManager.commit();
                // Mapping
                company = companyMapper.toEntity(rs);
            } catch (SQLException e) {
                System.out.println(
                        "Company DAO says : SQLException in getCompanyById "
                                + e.getMessage());
            } finally {
                connectionFactory.closeObject(ps);
                connectionFactory.closeObject(rs);
                // singleConnect.closeObject(connect);
                connectionManager.close();
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
        PreparedStatement ps = null;
        ResultSet results = null;

        try {
            // connect = singleConnect.getConnection();
            connectionManager.init();
            connect = connectionManager.get();
            ps = connect.prepareStatement(COUNT_COMPANIES);
            results = ps.executeQuery();
            connectionManager.commit();
            if (results.next()) {
                count = results.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Company DAO says : SQLException in getCount "
                    + e.getMessage());
        } finally {
            connectionFactory.closeObject(ps);
            connectionFactory.closeObject(results);
            // singleConnect.closeObject(connect);
            connectionManager.close();
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
        // connect = singleConnect.getConnection();
        connectionManager.init();
        connect = connectionManager.get();
        PreparedStatement psCompany = null;
        PreparedStatement psComputers = null;
        int count = 0;

        try {
            // connect.setAutoCommit(false);

            psComputers = connect
                    .prepareStatement(ComputerDAO.DELETE_BY_COMPANY);
            psComputers.setLong(1, id);
            count = psComputers.executeUpdate();

            if (count > 0) {
                System.out.println(count + " computers deleted !");
                LOGGER.debug(count + " computers deleted !");
            } else {
                System.out.println(
                        "Fail to delete computers with company_id = " + id);
                LOGGER.error(
                        "Fail to delete computers with company_id = " + id);
            }

            psCompany = connect.prepareStatement(DELETE_COMPANY);
            psCompany.setLong(1, id);
            count = psCompany.executeUpdate();
            if (count > 0) {
                System.out
                        .println("Company " + id + " was successfully deleted");
                LOGGER.debug("company " + id + " was successfully deleted");
            } else {
                System.out.println("Fail to delete company " + id);
                LOGGER.error("Fail to delete company " + id);
            }

            connectionManager.commit();
        } catch (SQLException e) {
            LOGGER.error("Company DAO says : SQLException in deleteCompany "
                    + e.getMessage());
            // TODO wrap in DAOException and throw it

            connectionManager.rollback();
            LOGGER.debug("rollback executed !");

        } finally {
            connectionFactory.closeObject(psComputers);
            connectionFactory.closeObject(psCompany);
            // singleConnect.closeObject(connect);
            connectionManager.close();
        }
    }

    /**
     * Delete a company by its id
     *
     * @param id
     *            the id of the company to delete
     * @param connection
     *            connection provided by service, (autocommit has to be set to
     *            false if you want to roll back!)
     */
    public void deleteCompany(long id, Connection connection) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(DELETE_COMPANY);
            ps.setLong(1, id);
            int count = ps.executeUpdate();
            if (count > 0) {
                System.out
                        .println("Company " + id + " was successfully deleted");
                LOGGER.debug("company " + id + " was successfully deleted");
            } else {
                System.out.println("Fail to delete company " + id);
                LOGGER.error("Fail to delete company " + id);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException in deleteCompany " + e.getMessage());
            throw new DAOException();
        } finally {
            connectionFactory.closeObject(ps);
        }

    }
}
