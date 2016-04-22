package mappers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import entities.Company;
import util.UtilQuerySQL;

public class CompanyMapper implements AbstractMapper<Company>, UtilQuerySQL {
	private static final Logger logger = LoggerFactory.getLogger(CompanyMapper.class);
	private static CompanyMapper instance;

	private CompanyMapper() {
		logger.debug("f_CompanyMapper constructor");
	}

	public static CompanyMapper getInstance() {
		logger.debug("f_getInstance");
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
	public List<Company> convertResultSet(ResultSet rs) {
		logger.debug("f_convertResultSet");
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
	public Company convertIntoEntity(ResultSet rs) {
		logger.debug("f_convertIntoEntity");
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
	public void attachEntityToRequest(PreparedStatement ps, Company entity, boolean hasToBeCreated) {}

}
