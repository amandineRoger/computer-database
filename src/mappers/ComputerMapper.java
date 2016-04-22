package mappers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import control.CLI;
import entities.Company;
import entities.Computer;
import util.UtilDate;
import util.UtilQuerySQL;

public class ComputerMapper implements AbstractMapper<Computer> {
	private static final Logger logger = LoggerFactory.getLogger(ComputerMapper.class);
	private static ComputerMapper instance = null;

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
	public List<Computer> convertResultSet(ResultSet rs) {
		logger.debug("f_convertResultSet");
		long idCompany;
		Company tmpCompany;
		Computer tmp;

		ArrayList<Computer> computers = new ArrayList<>();

		try {
			while (rs.next()) {
				Computer.Builder builder = new Computer.Builder(rs.getString(UtilQuerySQL.COL_NAME));
				idCompany = rs.getLong(UtilQuerySQL.COL_COMPANY_ID);
				// Company insertion
				if (idCompany != 0) {
					tmpCompany = new Company();
					tmpCompany.setId(idCompany);
					tmpCompany.setName(rs.getString(UtilQuerySQL.COL_COMPANY_NAME));
					builder.company(tmpCompany);
				}

				tmp = builder.introduced(UtilDate.timeStampToLocalDate(rs.getTimestamp(UtilQuerySQL.COL_INTRODUCED)))
						.discontinued(UtilDate.timeStampToLocalDate(rs.getTimestamp(UtilQuerySQL.COL_DISCONTINUED)))
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
	public Computer convertIntoEntity(ResultSet rs) {
		logger.debug("f_convertIntoEntity");
		long idCompany;
		Company tmpCompany;
		Computer tmpComputer = null;

		try {
			if (rs.next()) {
				Computer.Builder builder = new Computer.Builder(rs.getString(UtilQuerySQL.COL_NAME));
				idCompany = rs.getLong(UtilQuerySQL.COL_COMPANY_ID);
				// Company insertion
				if (idCompany != 0) {
					tmpCompany = new Company();
					tmpCompany.setId(idCompany);
					tmpCompany.setName(rs.getString(UtilQuerySQL.COL_COMPANY_NAME));
					builder.company(tmpCompany);
				}

				tmpComputer = builder
						.introduced(UtilDate.timeStampToLocalDate(rs.getTimestamp(UtilQuerySQL.COL_INTRODUCED)))
						.discontinued(UtilDate.timeStampToLocalDate(rs.getTimestamp(UtilQuerySQL.COL_DISCONTINUED)))
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
	public void attachEntityToRequest(PreparedStatement ps, Computer entity, boolean hasToBeCreated) {
		logger.debug("f_attachEntityToRequest");
		try {
			ps.setString(1, entity.getName());
			LocalDate date = entity.getIntroduced();
			if (date != null) {
				ps.setTimestamp(2, Timestamp.valueOf(date.atStartOfDay()));
			} else {
				ps.setNull(2, java.sql.Types.TIMESTAMP);
			}

			if ((date = entity.getDiscontinued()) != null) {
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
			//throw new mapperException();
		}
	}

}
