package com.excilys.cdb.mappers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.entities.Company;
import com.excilys.cdb.entities.Computer;
import com.excilys.cdb.util.UtilDate;
import com.excilys.cdb.util.UtilQuerySQL;

/**
 * ComputerMapper allows to translate results set which contains Computer into
 * Computer entity.
 *
 * @author Amandine Roger
 *
 */
public class ComputerMapper implements AbstractMapper<Computer> {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ComputerMapper.class);
    private static ComputerMapper instance = null;

    /**
     * getInstance (singleton method).
     *
     * @return the unique instance of computer mapper
     */
    public static ComputerMapper getInstance() {
        if (instance == null) {
            synchronized (ComputerMapper.class) {
                if (instance == null) {
                    instance = new ComputerMapper();
                }
            }
        }
        return instance;
    }

    @Override
    public final List<Computer> convertResultSet(final ResultSet rs) {
        LOGGER.debug("f_convertResultSet");
        long idCompany;
        Company tmpCompany;
        Computer tmp;

        ArrayList<Computer> computers = new ArrayList<>();

        try {
            while (rs.next()) {
                Computer.Builder builder = new Computer.Builder(
                        rs.getString(UtilQuerySQL.COL_NAME));
                idCompany = rs.getLong(UtilQuerySQL.COL_COMPANY_ID);
                // Company insertion
                if (idCompany != 0) {
                    tmpCompany = new Company();
                    tmpCompany.setId(idCompany);
                    tmpCompany.setName(
                            rs.getString(UtilQuerySQL.COL_COMPANY_NAME));
                    builder.company(tmpCompany);
                }

                tmp = builder
                        .introduced(UtilDate.timeStampToLocalDate(
                                rs.getTimestamp(UtilQuerySQL.COL_INTRODUCED)))
                        .discontinued(UtilDate.timeStampToLocalDate(
                                rs.getTimestamp(UtilQuerySQL.COL_DISCONTINUED)))
                        .build();
                tmp.setId(rs.getLong(UtilQuerySQL.COL_ID));

                computers.add(tmp);

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return computers;
    }

    @Override
    public final Computer convertIntoEntity(final ResultSet rs) {
        LOGGER.debug("f_convertIntoEntity");
        long idCompany;
        Company tmpCompany;
        Computer tmpComputer = null;

        try {
            if (rs.next()) {
                Computer.Builder builder = new Computer.Builder(
                        rs.getString(UtilQuerySQL.COL_NAME));
                idCompany = rs.getLong(UtilQuerySQL.COL_COMPANY_ID);
                // Company insertion
                if (idCompany != 0) {
                    tmpCompany = new Company();
                    tmpCompany.setId(idCompany);
                    tmpCompany.setName(
                            rs.getString(UtilQuerySQL.COL_COMPANY_NAME));
                    builder.company(tmpCompany);
                }

                tmpComputer = builder
                        .introduced(UtilDate.timeStampToLocalDate(
                                rs.getTimestamp(UtilQuerySQL.COL_INTRODUCED)))
                        .discontinued(UtilDate.timeStampToLocalDate(
                                rs.getTimestamp(UtilQuerySQL.COL_DISCONTINUED)))
                        .build();
                tmpComputer.setId(rs.getLong(UtilQuerySQL.COL_ID));

            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return tmpComputer;
    }

    @Override
    public final void attachEntityToRequest(final PreparedStatement ps,
            final Computer entity, final boolean hasToBeCreated) {
        LOGGER.debug("f_attachEntityToRequest");
        try {
            ps.setString(1, entity.getName());
            LocalDate date = entity.getIntroduced();
            if (date != null) {
                ps.setTimestamp(2, Timestamp.valueOf(date.atStartOfDay()));
            } else {
                ps.setNull(2, java.sql.Types.TIMESTAMP);
            }

            date = entity.getDiscontinued();
            if (date != null) {
                ps.setTimestamp(3, Timestamp.valueOf(date.atStartOfDay()));
            } else {
                ps.setNull(3, java.sql.Types.TIMESTAMP);
            }

            if (entity.getCompany() != null) {
                ps.setLong(4, entity.getCompany().getId());
            } else {
                ps.setNull(4, java.sql.Types.BIGINT);
            }

            if (!hasToBeCreated) {
                ps.setLong(5, entity.getId());
            }
        } catch (SQLException e) {
            // throw new mapperException();
            LOGGER.error(e.getMessage());
        }
    }

    /**
     * Convert a computer into computerDTO
     *
     * @param computer
     *            computer to convert
     * @return computerDTO based on computer
     */
    public ComputerDTO computerToDTO(Computer computer) {
        ComputerDTO dto = null;

        if (computer != null) {
            dto = new ComputerDTO();
            dto.setId(((Long) computer.getId()).toString());
            dto.setName(computer.getName());
            LocalDate date = computer.getIntroduced();
            if (date != null) {
                dto.setIntroduced(date.toString());
            }
            date = computer.getDiscontinued();
            if (date != null) {
                dto.setDiscontinued(date.toString());
            }
            Company company = computer.getCompany();
            if (company != null) {
                dto.setCompanyId(((Long) company.getId()).toString());
                dto.setCompanyName(company.getName());
            }
        }
        return dto;
    }

}
