package com.excilys.cdb.mappers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.entities.Company;
import com.excilys.cdb.util.UtilQuerySQL;

/**
 * CompanyMapper allows to translate results set which contains Company(ies)
 * into Company entity(ies).
 *
 * @author Amandine Roger
 *
 */
public final class CompanyMapper
        implements AbstractMapper<Company>, UtilQuerySQL {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(CompanyMapper.class);

    private static CompanyMapper instance;

    /**
     * private constructor for CompanyMapper (Singleton pattern).
     */
    private CompanyMapper() {
        LOGGER.debug("f_CompanyMapper constructor");
    }

    /**
     * getInstance (singleton method).
     *
     * @return the unique instance of company mapper
     */
    public static CompanyMapper getInstance() {
        LOGGER.debug("f_getInstance");
        if (instance == null) {
            synchronized (CompanyMapper.class) {
                if (instance == null) {
                    instance = new CompanyMapper();
                }
            }
        }
        return instance;
    }

    @Override
    public List<Company> convertResultSet(final ResultSet rs) {
        LOGGER.debug("f_convertResultSet");
        ArrayList<Company> companies = new ArrayList<>();
        Company tmp = null;
        try {
            while (rs.next()) {
                tmp = new Company();
                tmp.setId(rs.getLong(COL_ID));
                tmp.setName(rs.getString(COL_NAME));
                companies.add(tmp);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return companies;
    }

    @Override
    public Company toEntity(final ResultSet rs) {
        LOGGER.debug("f_convertIntoEntity");
        Company company = null;
        try {
            if (rs.next()) {
                company = new Company();
                company.setId(rs.getLong(COL_ID));
                company.setName(rs.getString(COL_NAME));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return company;
    }

    @Override
    public void attachEntityToRequest(final PreparedStatement ps,
            final Company entity, final boolean hasToBeCreated) {
    }

}
