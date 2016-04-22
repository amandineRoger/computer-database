package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import control.CLI;
import entities.Company;
import mappers.CompanyMapper;
import util.UtilQuerySQL;

public class CompanyDAO implements UtilQuerySQL {
	private static final Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	private static CompanyDAO instance;
	
	private SingleConnect singleConnect;
	private Connection connect;
	private CompanyMapper companyMapper;

	private CompanyDAO() {
		logger.debug("f_CompanyDAO constructor");
		this.singleConnect = SingleConnect.getInstance();
		this.companyMapper = CompanyMapper.getInstance();
	}
	public static CompanyDAO getInstance(){
		if (instance == null){
			synchronized (CompanyDAO.class) {
				if (instance == null){
					instance = new CompanyDAO();
				}
			}
		}
		return instance;
	}

	/**
	 * Get all companies from database
	 * 
	 * @return ArrayList<Company> all companies
	 */
	public ArrayList<Company> getCompanyList() {
		logger.debug("f_getCompanyList");
		ArrayList<Company> list = new ArrayList<>();
		ResultSet results = null;
		connect = singleConnect.getConnection();

		try {
			PreparedStatement ps = connect.prepareStatement(ALL_COMPANIES);
			results = ps.executeQuery();

			list = (ArrayList<Company>) companyMapper.convertResultSet(results);

			ps.close();
			results.close();
			connect.close();
		} catch (SQLException e) {
			System.out.println("Company DAO says : getCompanyList _ " + e.getMessage());
		}

		return list;
	}

	/**
	 * Find a company by its id
	 * 
	 * @param id
	 *            id of the wanted company
	 * @return wanted company
	 */
	public Company getCompanyById(long id) {
		logger.debug("f_getCompanyById");
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
				System.out.println("Company DAO says : getCompanyById " + e.getMessage());
			}
		}
		return company;
	}

}
