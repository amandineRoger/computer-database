package com.excilys.cdb.mappers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.cdb.entities.Company;

/**
 * CompanyMapper allows to translate results set which contains Company(ies)
 * into Company entity(ies).
 *
 * @author Amandine Roger
 *
 */

@Component("companyMapper")
@Scope("singleton")
public class CompanyMapper
        implements AbstractMapper<Company>, RowMapper<Company> {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(CompanyMapper.class);
    private static final String TAG = "CompanyMapper says _ ";

    @Override
    public List<Company> convertResultSet(final ResultSet rs) {
        LOGGER.debug(TAG + "f_convertResultSet");
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
            LOGGER.error(
                    TAG + "SQLException in convertResultSet " + e.getMessage());
            throw new MapperException(e);
        }
        return companies;
    }

    @Override
    public Company toEntity(final ResultSet rs) {
        LOGGER.debug(TAG + "f_convertIntoEntity");
        Company company = null;
        try {
            if (rs.next()) {
                company = new Company();
                company.setId(rs.getLong(COL_ID));
                company.setName(rs.getString(COL_NAME));
            }
        } catch (SQLException e) {
            LOGGER.error(TAG + "SQLException in toEntity " + e.getMessage());
            throw new MapperException(e);
        }
        return company;
    }

    @Override
    public void attachEntityToRequest(final PreparedStatement ps,
            final Company entity, final boolean hasToBeCreated) {
    }

    @Override
    public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
        LOGGER.debug(TAG + "f_mapRow");
        Company company = null;
        try {
            company = new Company();
            company.setId(rs.getLong(COL_ID));
            company.setName(rs.getString(COL_NAME));
        } catch (SQLException e) {
            LOGGER.error(TAG + "SQLException in toEntity " + e.getMessage());
            throw new MapperException(e);
        }
        return company;
    }
}
