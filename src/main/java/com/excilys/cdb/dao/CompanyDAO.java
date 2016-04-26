package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.entities.Company;
import com.excilys.cdb.mappers.CompanyMapper;
import com.excilys.cdb.util.UtilQuerySQL;

/**
 * Data Access Object for Company objects and requests Singleton pattern.
 *
 * @author Amandine Roger
 *
 */
public class CompanyDAO implements UtilQuerySQL {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(CompanyDAO.class);
    private static CompanyDAO instance;

    private SingleConnect singleConnect;
    private Connection connect;
    private CompanyMapper companyMapper;

    /**
     * private constructor for CompanyDAO (Singleton pattern).
     */
    private CompanyDAO() {
        LOGGER.debug("f_CompanyDAO constructor");
        this.singleConnect = SingleConnect.getInstance();
        this.companyMapper = CompanyMapper.getInstance();
    }

    /**
     * getInstance (singleton method).
     *
     * @return the unique instance of CompanyDAO
     */
    public static CompanyDAO getInstance() {
        if (instance == null) {
            synchronized (CompanyDAO.class) {
                if (instance == null) {
                    instance = new CompanyDAO();
                }
            }
        }
        return instance;
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
        ArrayList<Company> list = new ArrayList<>(limit);
        ResultSet results = null;
        connect = singleConnect.getConnection();

        try {
            PreparedStatement ps = connect.prepareStatement(ALL_COMPANIES_P);
            ps.setInt(1, offset);
            ps.setInt(2, limit);
            results = ps.executeQuery();

            list = (ArrayList<Company>) companyMapper.convertResultSet(results);

            ps.close();
            results.close();
            connect.close();
        } catch (SQLException e) {
            System.out.println(
                    "Company DAO says : getCompanyList _ " + e.getMessage());
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
            connect = singleConnect.getConnection();
            PreparedStatement ps;

            try {
                ps = connect.prepareStatement(COMPANY_BY_ID);
                ps.setLong(1, id);
                ResultSet rs = ps.executeQuery();

                // Mapping
                company = companyMapper.convertIntoEntity(rs);

                ps.close();
                rs.close();
                connect.close();
            } catch (SQLException e) {
                System.out.println(
                        "Company DAO says : getCompanyById " + e.getMessage());
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
        int count = 0;

        try {
            connect = singleConnect.getConnection();
            PreparedStatement ps = connect.prepareStatement(COUNT_COMPANIES);
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                count = result.getInt(1);
            }
            ps.close();
            result.close();
            connect.close();

        } catch (SQLException e) {
            System.out.println("CompanyDAO says: getCount " + e.getMessage());
        }

        return count;
    }

}
