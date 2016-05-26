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
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.entities.Company;
import com.excilys.cdb.entities.Computer;
import com.excilys.cdb.util.UtilDate;

/**
 * ComputerMapper allows to translate results set which contains Computer into
 * Computer entity.
 *
 * @author Amandine Roger
 *
 */
@Component("computerMapper")
@Scope("singleton")
public class ComputerMapper
        implements AbstractMapper<Computer>, RowMapper<Computer> {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ComputerMapper.class);

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
                        rs.getString(COL_NAME));
                idCompany = rs.getLong(COL_COMPANY_ID);
                // Company insertion
                if (idCompany != 0) {
                    tmpCompany = new Company();
                    tmpCompany.setId(idCompany);
                    tmpCompany.setName(rs.getString(COL_COMPANY_NAME));
                    builder.company(tmpCompany);
                }
                tmp = builder
                        .introduced(UtilDate.timeStampToLocalDate(
                                rs.getTimestamp(COL_INTRODUCED)))
                        .discontinued(UtilDate.timeStampToLocalDate(
                                rs.getTimestamp(COL_DISCONTINUED)))
                        .build();
                tmp.setId(rs.getLong(COL_ID));
                computers.add(tmp);
            }
        } catch (SQLException e) {
            LOGGER.error(
                    "ComputerMapper says : SQLException in convertResultSet "
                            + e.getMessage());
            // TODO wrap and throw new mapperException();
        }
        return computers;
    }

    @Override
    public final Computer toEntity(final ResultSet rs) {
        LOGGER.debug("f_convertIntoEntity");
        long idCompany;
        Company tmpCompany;
        Computer tmpComputer = null;

        try {
            if (rs.next()) {
                Computer.Builder builder = new Computer.Builder(
                        rs.getString(COL_NAME));
                idCompany = rs.getLong(COL_COMPANY_ID);
                // Company insertion
                if (idCompany != 0) {
                    tmpCompany = new Company();
                    tmpCompany.setId(idCompany);
                    tmpCompany.setName(rs.getString(COL_COMPANY_NAME));
                    builder.company(tmpCompany);
                }

                tmpComputer = builder
                        .introduced(UtilDate.timeStampToLocalDate(
                                rs.getTimestamp(COL_INTRODUCED)))
                        .discontinued(UtilDate.timeStampToLocalDate(
                                rs.getTimestamp(COL_DISCONTINUED)))
                        .build();
                tmpComputer.setId(rs.getLong(COL_ID));
            }
        } catch (SQLException e) {
            LOGGER.error("ComputerMapper says : SQLException in toEntity "
                    + e.getMessage());
            // TODO wrap and throw new mapperException();
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
            LOGGER.error(
                    "ComputerMapper says : SQLException in attachEntityToRequest "
                            + e.getMessage());
            // TODO wrap and throw new mapperException();
        }
    }

    /**
     * Convert a computer into computerDTO.
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

    /**
     * Convert the computer list of the page into ComputerDTO list.
     *
     * @param computers
     *            List of computers
     * @return List of computerDTO
     */
    public List<ComputerDTO> convertPageList(List<Computer> computers) {
        ArrayList<ComputerDTO> dtos = null;

        if (computers != null && (!computers.isEmpty())) {
            dtos = new ArrayList<>(computers.size());
            ComputerDTO tmp;

            for (Computer computer : computers) {
                tmp = computerToDTO(computer);
                dtos.add(tmp);
            }
        }
        return dtos;
    }

    /**
     * convert a computerDTO into a Computer.
     *
     * @param dto
     *            the computerDTO to convert
     * @return A computer instance corresponding to the DTO
     */
    public Computer dtoToComputer(ComputerDTO dto) {
        String tmp = dto.getName();
        Computer.Builder builder = new Computer.Builder(tmp);

        tmp = dto.getIntroduced();
        if (tmp != null && !tmp.trim().isEmpty()) {
            builder.introduced(UtilDate.stringToLocalDate(tmp));
        }

        tmp = dto.getDiscontinued();
        if (tmp != null && !tmp.trim().isEmpty()) {
            builder.discontinued(UtilDate.stringToLocalDate(tmp));
        }

        tmp = dto.getCompanyId();
        if (tmp != null && !tmp.trim().isEmpty()) {
            Company c = new Company();
            c.setId(Long.parseLong(tmp));
            c.setName(dto.getCompanyName());
            builder.company(c);
        }

        return builder.build();
    }

    @Override
    public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
        LOGGER.debug("f_mapRow");
        long idCompany;
        Company tmpCompany;
        Computer tmpComputer = null;

        try {
            Computer.Builder builder = new Computer.Builder(
                    rs.getString(COL_NAME));
            idCompany = rs.getLong(COL_COMPANY_ID);
            // Company insertion
            if (idCompany != 0) {
                tmpCompany = new Company();
                tmpCompany.setId(idCompany);
                tmpCompany.setName(rs.getString(COL_COMPANY_NAME));
                builder.company(tmpCompany);
            }

            tmpComputer = builder
                    .introduced(UtilDate.timeStampToLocalDate(
                            rs.getTimestamp(COL_INTRODUCED)))
                    .discontinued(UtilDate.timeStampToLocalDate(
                            rs.getTimestamp(COL_DISCONTINUED)))
                    .build();
            tmpComputer.setId(rs.getLong(COL_ID));
        } catch (SQLException e) {
            LOGGER.error("ComputerMapper says : SQLException in mapRow "
                    + e.getMessage());
            // TODO wrap and throw new mapperException();
        }
        return tmpComputer;
    }
}
